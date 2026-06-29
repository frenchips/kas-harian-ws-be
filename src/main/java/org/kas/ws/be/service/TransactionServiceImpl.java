package org.kas.ws.be.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.kas.ws.be.dto.request.TransactionRequest;
import org.kas.ws.be.dto.response.PaginationResponse;
import org.kas.ws.be.dto.response.TransactionResponse;
import org.kas.ws.be.model.Categories;
import org.kas.ws.be.model.Transactions;
import org.kas.ws.be.repository.CategoriesRepository;
import org.kas.ws.be.repository.TransactionRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TransactionServiceImpl implements TransactionService{

    @Inject
    private TransactionRepository transactionRepository;

    @Inject
    private CategoriesRepository categoriesRepository;

    @Override
    @Transactional
    public TransactionResponse createKas(TransactionRequest transactionRequest) {

        Transactions transactions = saveTransaction(transactionRequest);
        TransactionResponse transactionResponse = new TransactionResponse();

        transactionResponse.setId(transactions.getId());
        transactionResponse.setCategoriesId(transactions.getCategoriesId().getId());
        transactionResponse.setCategoriesName(transactions.getCategoriesId().getCategoryName());
        transactionResponse.setIncome(transactions.getIncome());
        transactionResponse.setExpand(transactions.getExpend());
        transactionResponse.setDescription(transactions.getDescription());
        transactionResponse.setTransactionDate(transactions.getTransactionDate());
        transactionResponse.setAmount(transactions.getAmount());
        return transactionResponse;
    }


    public Transactions saveTransaction(TransactionRequest transactionRequest){
        Transactions transactions = new Transactions();
        Categories categories = categoriesRepository.findById(transactionRequest.getCategoriesId());

        Integer income = (transactionRequest.getIncome() == null) ? 0 : transactionRequest.getIncome();
        Integer expend = (transactionRequest.getExpend() == null) ? 0 : transactionRequest.getExpend();

        transactions.setCategoriesId(categories);
        transactions.setDescription(transactionRequest.getDescription());
        transactions.setIncome(transactionRequest.getIncome());
        transactions.setExpend(transactionRequest.getExpend());

        // 1. Ambil saldo terakhir dari transaksi paling baru di DB
        Integer currentBalance = transactionRepository.find("ORDER BY id DESC").firstResultOptional()
                .map(Transactions::getAmount)
                .orElse(0); // Jika database masih kosong, saldo awal dimulai dari 0

        // 2. Hitung saldo berjalan baru berdasarkan tipe kategori
        // Asumsi: Entity Categories memiliki method getCategoryType() yang isinya "INCOME" atau "EXPEND"
        String type = categories.getCategoryName();
        transactions.setAmount(calculateTransaction(type, currentBalance, income, expend));

        transactions.setAmount(calculateTransaction(categories.getType(), currentBalance,transactions.getIncome(), transactions.getExpend()));
        transactions.setTransactionDate(new Timestamp(System.currentTimeMillis()));
        transactions.setCreateBy("Admin");
        transactions.setCreateAt(new Timestamp(System.currentTimeMillis()));
        transactions.setRecordFlag("N");
        transactionRepository.persist(transactions);
        return transactions;
    }


    private Integer calculateTransaction(String categoryType, Integer currentBalance, Integer income, Integer expend){

        if (categoryType != null && categoryType.equalsIgnoreCase("INCOME")) {
            // Saldo Saat Ini + Pemasukan
            return currentBalance + income;
        } else if (categoryType != null && categoryType.equalsIgnoreCase("EXPENSE")) {
            // Saldo Saat Ini - Pengeluaran
            return currentBalance - expend;
        }

        // Cadangan jika tipe tidak cocok, saldo tetap sama
        return currentBalance + income - expend;
    }

    @Override
    public PaginationResponse<TransactionResponse> getTransactionsPaginated(int page, int size) {
        List<Transactions> transactionsList = transactionRepository.findPaginatedNative(page, size);

        List<TransactionResponse> responseList = transactionsList.stream()
                .map(this::toTransactionResponse)
                .collect(Collectors.toList());

        long totalElements = transactionRepository.countNative();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        boolean isLast = (page + 1) >= totalPages;

        return new PaginationResponse<>(responseList, page, size, totalElements, totalPages, isLast);
    }

    private TransactionResponse toTransactionResponse(Transactions transactions) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transactions.getId());
        response.setCategoriesId(transactions.getCategoriesId().getId());
        response.setCategoriesName(transactions.getCategoriesId().getCategoryName());
        response.setIncome(transactions.getIncome());
        response.setExpand(transactions.getExpend());
        response.setDescription(transactions.getDescription());
        response.setTransactionDate(transactions.getTransactionDate());
        response.setAmount(transactions.getAmount());
        return response;
    }
}
