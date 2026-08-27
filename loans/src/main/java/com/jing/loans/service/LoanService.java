package com.jing.loans.service;

import com.jing.loans.dto.LoanDto;

public interface LoanService {

    void createLoan(String mobileNumber);

    LoanDto fetchLoan(String mobileNumber);

    boolean updateLoan(LoanDto loanDto);
    boolean deleteLoan(String mobileNumber);
}
