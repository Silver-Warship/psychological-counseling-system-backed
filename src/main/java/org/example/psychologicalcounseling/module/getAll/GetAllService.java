package org.example.psychologicalcounseling.module.getAll;

import org.example.psychologicalcounseling.model.Counsellor;
import org.example.psychologicalcounseling.model.Supervisor;
import org.example.psychologicalcounseling.model.User;
import org.example.psychologicalcounseling.module.user.login.UserLoginService;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.example.psychologicalcounseling.repository.SupervisorRepository;
import org.example.psychologicalcounseling.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class GetAllService {
    private final CounsellorRepository counsellorRepository;
    private final SupervisorRepository supervisorRepository;
    private final UserRepository userRepository;

    public GetAllService(CounsellorRepository counsellorRepository, SupervisorRepository supervisorRepository, UserRepository userRepository) {
        this.counsellorRepository = counsellorRepository;
        this.supervisorRepository = supervisorRepository;
        this.userRepository = userRepository;
    }

    /**
     * 获取所有 counsellor 或 supervisor 的信息
     *
     * @param role 角色类型，"counsellor" 或 "supervisor"
     * @return GetAllResponse 对象，包含所有 counsellor 或 supervisor 的信息
     */
    public GetAllResponse getAll(String role) {
        GetAllResponse getAllResponse = new GetAllResponse();
        List<GetAllResponse.Info> infoList = new ArrayList<>();

        if (role.equals("counsellor")) {
            // 使用线程池执行异步操作
            ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            // 假设从数据库中查询 counsellor 的数据
            List<Counsellor> counsellors = counsellorRepository.findAll();

            for (Counsellor counsellor : counsellors) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    infoList.add(
                            new GetAllResponse.Info(
                                    counsellor.getCounsellorID(),
                                    counsellor.getNickname(),
                                    counsellor.getEmail()
                            )
                    );
                }, executor);
                futures.add(future);
            }

            try {
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally {
                executor.shutdown();
            }
        } else if (role.equals("supervisor")) {
            // 使用线程池执行异步操作
            ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            // 假设从数据库中查询 supervisor 的数据
            List<Supervisor> supervisors = supervisorRepository.findAll();
            for (Supervisor supervisor : supervisors) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    infoList.add(
                            new GetAllResponse.Info(
                                    supervisor.getSupervisorID(),
                                    supervisor.getNickname(),
                                    supervisor.getEmail()
                            )
                    );
                }, executor);
                futures.add(future);
            }
            try {
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally {
                executor.shutdown();
            }

        } else {
            getAllResponse.setCode(500);
            getAllResponse.setCodeMsg("Bad Request : Role Error");
        }

        getAllResponse.setInfos(infoList);
        return getAllResponse;
    }

    /**
     * 模糊搜索 user、counsellor 或 supervisor 的信息
     *
     * @param id       user、counsellor 或 supervisor 的 ID
     * @param role     角色类型，“user”"counsellor" 或 "supervisor"
     * @param nickname user、counsellor 或 supervisor 的昵称
     * @return GetAllResponse 模糊搜索结果
     */
    public GetAllResponse fuzzySearch(Long id, String role, String nickname) {
        GetAllResponse getAllResponse = new GetAllResponse();
        List<GetAllResponse.Info> infoList = new ArrayList<>();

        if (nickname == null || nickname.isEmpty()){
            nickname = "welcomeToHuaShiXinTu";
        }

        if(id==null || id.toString().isEmpty()){
            id = (long) -1;
        }


        if ("user".equals(role)) {
            // 模糊查询用户信息
            List<User> users = userRepository.findByUidOrNicknameContainingIgnoreCase(id, nickname);
            for (User user : users) {
                infoList.add(new GetAllResponse.Info(
                        user.getUid(),
                        user.getNickname(),
                        user.getEmail()
                ));
            }
        } else if ("counsellor".equals(role)) {
            // 模糊查询咨询师信息
            List<Counsellor> counsellors = counsellorRepository.findByCounsellorIDOrNicknameContainingIgnoreCase(id, nickname);
            for (Counsellor counsellor : counsellors) {
                infoList.add(new GetAllResponse.Info(
                        counsellor.getCounsellorID(),
                        counsellor.getNickname(),
                        counsellor.getEmail()
                ));
            }
        } else if ("supervisor".equals(role)) {
            // 模糊查询督导信息
            List<Supervisor> supervisors = supervisorRepository.findBySupervisorIDOrNicknameContainingIgnoreCase(id, nickname);

            for (Supervisor supervisor : supervisors) {
                infoList.add(new GetAllResponse.Info(
                        supervisor.getSupervisorID(),
                        supervisor.getNickname(),
                        supervisor.getEmail()
                ));
            }
        } else {
            getAllResponse.setCode(500);
            getAllResponse.setCodeMsg("Bad Request: Invalid Role");
        }

        getAllResponse.setInfos(infoList);
        return getAllResponse;
    }

}
