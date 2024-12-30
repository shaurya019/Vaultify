package bank.security.services.imptl;

import bank.security.dto.*;
import bank.security.entity.User;
import bank.security.repository.UserRepository;
import bank.security.services.EmailService;
import bank.security.services.UserService;
import bank.security.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        if(userRepository.existByEmail(userRequest.getEmail())){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();


        User saveUser = userRepository.save(newUser);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(saveUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("Congratulations! Your Account Has been Successfully Created.\nYour Account Details: \n" +
                        "Account Name: " + saveUser.getFirstName() + " " + saveUser.getLastName() + " " + saveUser.getOtherName() + "\nAccount Number: " + saveUser.getAccountNumber())
                .build();
        emailService.sendEmailAlert(emailDetails);


        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder().accountName(saveUser.getFirstName() + " " + saveUser.getLastName() + " " + saveUser.getOtherName()).accountNumber(saveUser.getAccountNumber()).accountBalance(saveUser.getAccountBalance()).build())
                .build();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {
        return null;
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {
        return "";
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
        return null;
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {
        return null;
    }
}
