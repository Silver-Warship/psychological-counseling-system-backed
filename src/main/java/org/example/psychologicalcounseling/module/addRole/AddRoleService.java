package org.example.psychologicalcounseling.module.addRole;

import org.example.psychologicalcounseling.model.Account;
import org.example.psychologicalcounseling.model.Admin;
import org.example.psychologicalcounseling.model.Counsellor;
import org.example.psychologicalcounseling.model.Supervisor;
import org.example.psychologicalcounseling.repository.AccountRepository;
import org.example.psychologicalcounseling.repository.AdminRepository;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.example.psychologicalcounseling.repository.SupervisorRepository;
import org.springframework.stereotype.Service;

@Service
public class AddRoleService {
    private final SupervisorRepository supervisorRepository;
    private final CounsellorRepository counsellorRepository;
    private final AdminRepository adminRepository;
    private final AccountRepository accountRepository;

    public AddRoleService(SupervisorRepository supervisorRepository, CounsellorRepository counsellorRepository,
                          AdminRepository adminRepository, AccountRepository accountRepository) {
        this.supervisorRepository = supervisorRepository;
        this.counsellorRepository = counsellorRepository;
        this.adminRepository = adminRepository;
        this.accountRepository = accountRepository;
    }

    public boolean addCounsellor(String name, String email, String gender) {
        if (counsellorRepository.findByEmail(email) != null) {
            return false;
        }

        // add account
        Account account = new Account();
        account.setPassword("123456");
        Long uid = accountRepository.saveAndFlush(account).getAid();

        // add counsellor role
        Counsellor counsellor = new Counsellor();
        counsellor.setNickname(name);
        counsellor.setEmail(email);
        counsellor.setCounsellorID(uid);
        if (gender.equals("male")) {
            counsellor.setGender(Counsellor.Gender.male);
        } else if (gender.equals("female")) {
            counsellor.setGender(Counsellor.Gender.female);
        } else {
            counsellor.setGender(Counsellor.Gender.unknown);
        }

        // save counsellor
        counsellorRepository.save(counsellor);
        return true;
    }

    public boolean addSupervisor(String name, String email, String gender) {
        if (supervisorRepository.findByEmail(email) != null) {
            return false;
        }

        // add account
        Account account = new Account();
        account.setPassword("123456");
        Long uid = accountRepository.saveAndFlush(account).getAid();

        // add supervisor role
        Supervisor supervisor = new Supervisor();
        supervisor.setNickname(name);
        supervisor.setEmail(email);
        supervisor.setSupervisorID(uid);
        if (gender.equals("male")) {
            supervisor.setGender(Supervisor.Gender.male);
        } else if (gender.equals("female")) {
            supervisor.setGender(Supervisor.Gender.female);
        } else {
            supervisor.setGender(Supervisor.Gender.unknown);
        }

        // save supervisor
        supervisorRepository.save(supervisor);
        return true;
    }

    public boolean addAdmin(String name, String email, String gender) {
        if (adminRepository.findByEmail(email) != null) {
            return false;
        }

        // add account
        Account account = new Account();
        account.setPassword("123456");
        Long uid = accountRepository.saveAndFlush(account).getAid();

        // add admin role
        Admin admin = new Admin();
        admin.setNickname(name);
        admin.setEmail(email);
        admin.setAdminID(uid);
        if (gender.equals("male")) {
            admin.setGender(Admin.Gender.male);
        } else if (gender.equals("female")) {
            admin.setGender(Admin.Gender.female);
        } else {
            admin.setGender(Admin.Gender.unknown);
        }

        // save admin
        adminRepository.save(admin);
        return true;
    }
}
