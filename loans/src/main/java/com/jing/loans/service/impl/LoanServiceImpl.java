package com.jing.loans.service.impl;

import com.jing.loans.constant.LoanConstants;
import com.jing.loans.dto.LoanDto;
import com.jing.loans.entity.Loan;
import com.jing.loans.exception.LoanAlreadyExistsException;
import com.jing.loans.exception.ResourceNotFoundException;
import com.jing.loans.mapper.LoanMapper;
import com.jing.loans.repository.LoanRepository;
import com.jing.loans.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loan> optionalLoan = loanRepository.findByMobileNumber(mobileNumber);
        if (optionalLoan.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with mobile number " + mobileNumber);
        }
        loanRepository.save(initialLoan(mobileNumber));
    }


    @Override
    public LoanDto fetchLoan(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "Mobile Number", mobileNumber));
        return loanMapper.toDto(loan);
    }

    @Override
    public boolean updateLoan(LoanDto loanDto) {
        Loan loan = loanRepository.findByLoanNumber(loanDto.getLoanNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "Loan Number", loanDto.getLoanNumber()));
        loanMapper.updateLoan(loan, loanDto);
        loanRepository.save(loan);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "Mobile Number", mobileNumber));
        loanRepository.deleteById(loan.getLoanId());
        return true;
    }


    private Loan initialLoan(String mobileNumber) {
        Loan loan = new Loan();
        loan.setMobileNumber(mobileNumber);
        loan.setLoanNumber(generatedLoanNumber());
        loan.setLoanType(LoanConstants.HOME_LOAN);
        loan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        loan.setAmountPaid(0);
        loan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        return loan;
    }

    private String generatedLoanNumber(){
        String loanNumber;
        do{
            loanNumber = String.valueOf(100000000000L + new Random().nextInt(900000000));
        }while (loanRepository.existsByLoanNumber(loanNumber));
            return loanNumber;
    }
}
