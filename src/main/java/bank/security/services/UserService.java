package bank.security.services;

import bank.security.dto.BankResponse;
import bank.security.dto.UserRequest;
import bank.security.dto.EnquiryRequest;
import bank.security.dto.CreditDebitRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest request);
    String nameEnquiry(EnquiryRequest request);
    BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);
}
