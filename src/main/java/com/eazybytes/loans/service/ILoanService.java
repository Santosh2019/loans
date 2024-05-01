package com.eazybytes.loans.service;

import com.eazybytes.loans.dto.LoansDto;

public interface ILoanService {
    public void createLoan(String mobileNumber);

    public LoansDto fetchLoan(String mobileNumber);

    public boolean updateLoan(LoansDto loansDto);

    public boolean deleteLoan(String mobileNumber);
}
