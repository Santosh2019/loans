package com.eazybytes.loans.service;

import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.exception.LoanAlreadyExits;
import com.eazybytes.loans.exception.ResourceNotFoundException;
import com.eazybytes.loans.mapper.LoanMapper;
import com.eazybytes.loans.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class LoanServiceImpl implements ILoanService {

    private LoanRepository loanRepository;

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> checkedLoan = loanRepository.findByMobileNumber(mobileNumber);
        if (checkedLoan.isPresent()) {
            throw new LoanAlreadyExits("Your are already applied" + mobileNumber + "this mobile number");
        }
        Loans newLoan = new Loans();
        newLoan.setMobileNumber(mobileNumber);
        loanRepository.save(createNewLoan(mobileNumber));
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
        return LoanMapper.mapToLoanDto(loans, new LoansDto());

    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loanRepository.findByLoanNumber(loansDto.getLoanNumber()).
                orElseThrow(() -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoanMapper.mapToLoan(loansDto, loans);
        loanRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("", "mobileNumber", mobileNumber));
        loanRepository.deleteById(loans.getLoanId());
        return true;
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans loans = new Loans();
        long randomAccNumber = 10000000000L + new Random().nextInt(900000000);
        loans.setLoanNumber(Long.toString(randomAccNumber));
        loans.setLoanType(LoansConstants.HOME_LOAN);
        loans.setMobileNumber(mobileNumber);
        loans.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        loans.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return loans;
    }
}

