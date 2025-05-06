package org.example.psychologicalcounseling.module.info;


import org.example.psychologicalcounseling.model.Account;
import org.example.psychologicalcounseling.model.Counsellor;
import org.example.psychologicalcounseling.model.Supervisor;
import org.example.psychologicalcounseling.model.User;
import org.example.psychologicalcounseling.repository.AccountRepository;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.example.psychologicalcounseling.repository.SupervisorRepository;
import org.example.psychologicalcounseling.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountDeleteService {
    private final UserRepository userRepository;
    private final CounsellorRepository counsellorRepository;
    private final SupervisorRepository supervisorRepository;
    private final AccountRepository accountRepository;

    public AccountDeleteService(UserRepository userRepository, CounsellorRepository counsellorRepository, SupervisorRepository supervisorRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.counsellorRepository = counsellorRepository;
        this.supervisorRepository = supervisorRepository;
        this.accountRepository = accountRepository;
    }

    public boolean deleteUser(Long id) {

        User user = userRepository.findById(id).orElse(null);
        Account account = accountRepository.findById(id).orElse(null);
        if (user != null && account != null) {
            userRepository.delete(user);
            accountRepository.delete(account);
            return true;
        }
        return false;

    }

    public boolean deleteCounsellor(Long id) {
        Counsellor counsellor = counsellorRepository.findById(id).orElse(null);
        Account account = accountRepository.findById(id).orElse(null);
        if (counsellor != null && account != null) {
            counsellorRepository.delete(counsellor);
            accountRepository.delete(account);
            return true;
        }
        return false;
    }

    public boolean deleteSupervisor(Long id) {
        Supervisor supervisor = supervisorRepository.findById(id).orElse(null);
        Account account = accountRepository.findById(id).orElse(null);
        if (supervisor != null && account != null) {
            supervisorRepository.delete(supervisor);
            accountRepository.delete(account);
            return true;
        }
        return false;
    }
}
