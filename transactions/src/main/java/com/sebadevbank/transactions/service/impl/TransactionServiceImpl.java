package com.sebadevbank.transactions.service.impl;

import com.sebadevbank.transactions.dto.TransactionDto;
import com.sebadevbank.transactions.dto.UpdateBalanceRequest;
import com.sebadevbank.transactions.entity.Transaction;
import com.sebadevbank.transactions.exception.ForbiddenException;
import com.sebadevbank.transactions.exception.ResourceNotFoundException;
import com.sebadevbank.transactions.mapper.TransactionMapper;
import com.sebadevbank.transactions.repository.TransactionRepository;
import com.sebadevbank.transactions.service.ITransactionService;
import com.sebadevbank.transactions.service.client.AccountFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private final AccountFeignClient accountFeignClient;
    private final TransactionRepository transactionRepository;

    @Override
    public List<TransactionDto> getLastTransactions(Long accountNumber, int limit) {
        List<Transaction> lastTransactions = transactionRepository
                .findTopNByAccountIdOrderByTimestampDesc(accountNumber, PageRequest.of(0,limit))
                .getContent();//PageRequest.of(0, limit) para pasar dinámicamente el límite de transacciones.
        return lastTransactions.stream()
                .map(t->TransactionMapper.mapToTransactionDto(t,new TransactionDto()))
                .collect(Collectors.toList())
        ;
    }

    @Override
    public List<TransactionDto> getAllTransactions(Long accountNumber) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountNumber);
        return transactions.stream()
                .map(t-> TransactionMapper.mapToTransactionDto(
                        t,new TransactionDto()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto getActivity(Long accountNumber, Long transferId, Jwt jwt) {
        // Extraer el userId del token JWT
        String userId = jwt.getSubject();
        validateUserAccess(accountNumber, userId, jwt);

        Transaction transaction = transactionRepository
                .findByAccountIdAndId(accountNumber, transferId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Transacción no encontrada para la cuenta y ID proporcionados"
                ));

        return TransactionMapper.mapToTransactionDto(transaction, new TransactionDto());
    }

    private void validateUserAccess(Long accountNumber, String userId, Jwt jwt) {
        // Verificar si el usuario tiene acceso a la cuenta
        boolean hasAccess = accountFeignClient.validateUserAccess(accountNumber, userId);
        if (!hasAccess) {
            throw new ForbiddenException("Usuario no tiene acceso a esta cuenta");
        }

        // Verificar roles del usuario (opcional)
        List<String> roles = getRolesFromJwt(jwt);
        if (roles == null || !roles.contains("ACCOUNTS")) {
            throw new ForbiddenException("Usuario no tiene el rol necesario para acceder a esta cuenta");
        }
    }

    public List<String> getRolesFromJwt(Jwt jwt) {
        // Obtener el objeto "realm_access" como un Map
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");

        // Verificar si el objeto "realm_access" existe y contiene la clave "roles"
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            // Obtener la lista de roles
            return (List<String>) realmAccess.get("roles");
        }

        // Si no hay roles, devolver una lista vacía
        return List.of();
    }

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        //guardo TX en DB
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDto, new Transaction());
        Transaction savedTransaction = transactionRepository.save(transaction);

        //Notificar a Account para actualizar saldo
        UpdateBalanceRequest updateBalance = new UpdateBalanceRequest(
                savedTransaction.getAccountId(),
                savedTransaction.getAmount(),
                savedTransaction.getTransactionType()
        );

        //comunicacion con Account MS
        accountFeignClient.updateAccountBalance(updateBalance);

        return TransactionMapper.mapToTransactionDto(savedTransaction,new TransactionDto());
    }
}
