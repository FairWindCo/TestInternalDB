package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.*;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.FilesType;
import ua.pp.fairwind.internalDBSystem.services.repository.*;

/**
 * Created by Сергей on 27.07.2015.
 */
@Controller
@RequestMapping("/initial")
public class StartInitController {
    @Autowired
    private FileTypeRepository filetypesrep;
    @Autowired
    private UserRepository userrep;
    @Autowired
    private RoleRepository rolrep;
    @Autowired
    private SubdivisionRepository subdivrep;
    @Autowired
    private CategoryRepository catrep;
    @Autowired
    private InfoTypeRepository inforep;


    @Transactional
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String init(Model model) {
        initUserSystem();
        initFileType();
        return "initok";
    }

    private void initUserSystem(){
        Roles role_admin=createRole("ROLE_ADMIN","Администрирование пользователей и их прав");
        Roles role_superview=createRole("ROLE_SUPERVIEW","Просмотр любой информации");
        Roles role_superedit=createRole("ROLE_SUPEREDIT","Редактирование любых карточек");
        Roles role_superinfedit=createRole("ROLE_SUPER_INF_EDIT","Редактирование справочников любых подразделений");
        Roles role_confidential=createRole("ROLE_CONFIDENTIAL","Доступ к конфиденциальной информации");
        Roles role_globalinfoedit=createRole("ROLE_GLOBAL_INFO_EDIT","Редактирование глобальных справочников");
        Roles role_task=createRole("ROLE_ADD_TASK","Добавление жалоб");
        Roles role_mainview=createRole("ROLE_MAIN_VIEW","Просмотр данных только своего подразделения");
        Roles role_mainedit=createRole("ROLE_MAIN_EDIT","Редактирование данных только своего подразделения");
        Roles role_maininfedit=createRole("ROLE_MAIN_INF_EDIT","Редактрирование справочников только своего подразделения");
        Roles role_groupview=createRole("ROLE_GROUP_VIEW","Просмотр данных доверенных подразделений");
        Roles role_groupedit=createRole("ROLE_GROUP_EDIT","Редактирование данных доверенных подразделений");
        Roles role_groupinfedit=createRole("ROLE_GROUP_INF_EDIT","Редактрирование справочников доверенных подразделений");
        Roles role_clientview=createRole("ROLE_CLIENT_VIEW","Просмотр карточек клиентов");
        Roles role_clientedit=createRole("ROLE_CLIENT_EDIT","Редактирование информации в карточке клиента");
        Roles role_personalview=createRole("ROLE_PERSONAL_VIEW","Просмотр карточек персонала");
        Roles role_personaledit=createRole("ROLE_PERSONAL_EDIT","Редактирование карточек персонала");
        Roles role_personaladd=createRole("ROLE_PERSONAL_ADD","Добаление сотрудников в картотеку");

        rolrep.save(role_superview);
        rolrep.save(role_superedit);
        rolrep.save(role_superinfedit);
        rolrep.save(role_mainview);
        rolrep.save(role_mainedit);
        rolrep.save(role_maininfedit);
        rolrep.save(role_groupview);
        rolrep.save(role_groupedit);
        rolrep.save(role_groupinfedit);
        rolrep.save(role_clientview);
        rolrep.save(role_clientedit);
        rolrep.save(role_personalview);
        rolrep.save(role_personaledit);
        rolrep.save(role_personaladd);
        rolrep.save(role_confidential);
        rolrep.save(role_admin);
        rolrep.save(role_globalinfoedit);
        rolrep.save(role_task);/**/

        User user1=new User();
        User user2=new User();
        User user3=new User();
        user1.setUserName("administrator");
        user1.setFIO("Иванов И.И.");
        user1.setEnabled(true);
        user2.setFIO("Петров П.П.");
        user2.setUserName("power_user");
        user2.setEnabled(true);
        user3.setFIO("Сидоров С.С.");
        user3.setUserName("user");
        user3.setEnabled(true);

        user1.addUserRoles(role_admin);
        user1.addUserRoles(role_globalinfoedit);

        user2.addUserRoles(role_clientview);
        user2.addUserRoles(role_clientedit);
        user2.addUserRoles(role_personalview);
        user2.addUserRoles(role_personaledit);
        user2.addUserRoles(role_superview);
        user2.addUserRoles(role_superedit);
        user2.addUserRoles(role_superinfedit);
        user2.addUserRoles(role_confidential);

        user3.addUserRoles(role_clientview);
        user3.addUserRoles(role_mainview);
        user3.addUserRoles(role_maininfedit);

        Subdivision sub1_it=createSubDiv("IT отдел");
        Subdivision sub2_sb=createSubDiv("Служба Безопасности");
        Subdivision sub3_rest=createSubDiv("Ресторан");
        Subdivision sub4_fitnes=createSubDiv("Фитнес");
        Subdivision sub5_spa=createSubDiv("СПА");
        Subdivision sub6_medec=createSubDiv("Медицина");
        Subdivision sub7_hr=createSubDiv("HR");

        Category task=createCategory("Жалоба");
        Category info=createCategory("Информация");
        Category health=createCategory("Состояние здоровья");
        Category client=createCategory("предпочтения клиента");

        InfoType info1=createInfoType("Перенесенные заболевания");
        InfoType info2=createInfoType("Предложить клиенту");

        sub1_it.addCategory(task);
        sub1_it.addCategory(info);

        sub2_sb.addCategory(task);
        sub2_sb.addCategory(info);
        sub2_sb.addCategory(health);
        sub2_sb.addCategory(client);

        sub3_rest.addCategory(task);
        sub3_rest.addCategory(info);
        sub3_rest.addCategory(client);

        sub4_fitnes.addCategory(task);
        sub4_fitnes.addCategory(info);
        sub4_fitnes.addCategory(client);

        sub5_spa.addCategory(task);
        sub5_spa.addCategory(info);
        sub5_spa.addCategory(client);

        sub6_medec.addCategory(task);
        sub6_medec.addCategory(info);
        sub6_medec.addCategory(health);
        sub6_medec.addCategory(client);

        sub7_hr.addCategory(task);
        sub7_hr.addCategory(info);


        health.addInfoTypes(info1);
        client.addInfoTypes(info2);


        //catrep.save(task);
        //catrep.save(info);
        //catrep.save(health);
        //catrep.save(client);

        //inforep.save(info1);
        //inforep.save(info2);
/*
        subdivrep.save(sub1_it);
        subdivrep.save(sub2_sb);
        subdivrep.save(sub3_rest);
        subdivrep.save(sub4_fitnes);
        subdivrep.save(sub5_spa);
        subdivrep.save(sub6_medec);
        subdivrep.save(sub7_hr);/**/

        userrep.save(user1);
        userrep.save(user2);
        userrep.save(user3);

    }

    private Roles createRole(String name,String description){
        Roles role=new Roles();
        role.setRoleDescription(description);
        role.setRoleName(name);
        return role;
    }

    private Subdivision createSubDiv(String name){
        Subdivision subDiv=new Subdivision();
        subDiv.setName(name);
        return subDiv;
    }

    private Category createCategory(String name){
        Category categor=new Category();
        categor.setName(name);
        return categor;
    }

    private InfoType createInfoType(String name){
        InfoType info=new InfoType();
        info.setTypeName(name);
        return info;
    }

    private void initFileType(){
        FilesType ft1=new FilesType();
        ft1.setFilesTypeName("Изображение / Images");
        filetypesrep.save(ft1);
        FilesType ft2=new FilesType();
        ft2.setFilesTypeName("Видео / Videos");
        filetypesrep.save(ft2);
        FilesType ft3=new FilesType();
        ft3.setFilesTypeName("Звук / Sounds");
        filetypesrep.save(ft3);
        FilesType ft4=new FilesType();
        ft4.setFilesTypeName("Документ / Documents");
        filetypesrep.save(ft4);
    }

    public FileTypeRepository getFiletypesrep() {
        return filetypesrep;
    }

    public void setFiletypesrep(FileTypeRepository filetypesrep) {
        this.filetypesrep = filetypesrep;
    }

    public UserRepository getUserrep() {
        return userrep;
    }

    public void setUserrep(UserRepository userrep) {
        this.userrep = userrep;
    }

    public RoleRepository getRolrep() {
        return rolrep;
    }

    public void setRolrep(RoleRepository rolrep) {
        this.rolrep = rolrep;
    }

    public SubdivisionRepository getSubdivrep() {
        return subdivrep;
    }

    public void setSubdivrep(SubdivisionRepository subdivrep) {
        this.subdivrep = subdivrep;
    }

    public CategoryRepository getCatrep() {
        return catrep;
    }

    public void setCatrep(CategoryRepository catrep) {
        this.catrep = catrep;
    }

    public InfoTypeRepository getInforep() {
        return inforep;
    }

    public void setInforep(InfoTypeRepository inforep) {
        this.inforep = inforep;
    }
}
