package com.jing.loans.mapper;

import com.jing.loans.dto.LoanDto;
import com.jing.loans.entity.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanDto toDto(Loan loan) {
        if (loan == null) {
            return null;
        }
        LoanDto loanDto = new LoanDto();
        loanDto.setMobileNumber(loan.getMobileNumber());
        loanDto.setLoanNumber(loan.getLoanNumber());
        loanDto.setLoanType(loan.getLoanType());
        loanDto.setAmountPaid(loan.getAmountPaid());
        loanDto.setTotalLoan(loan.getTotalLoan());
        loanDto.setOutstandingAmount(loan.getOutstandingAmount());
        return  loanDto;
    }

    public void updateLoan(Loan loan, LoanDto loanDto) {
        if (loan == null) return ;
        if(loanDto == null) return;
        loan.setMobileNumber(loanDto.getMobileNumber());
        loan.setLoanNumber(loanDto.getLoanNumber());
        loan.setLoanType(loanDto.getLoanType());
        loan.setAmountPaid(loanDto.getAmountPaid());
        loan.setOutstandingAmount(loanDto.getOutstandingAmount());
        loan.setTotalLoan(loanDto.getTotalLoan());

    }
}
