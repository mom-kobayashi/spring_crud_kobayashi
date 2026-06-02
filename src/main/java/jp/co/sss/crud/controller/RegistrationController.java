package jp.co.sss.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;
import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.repository.DepartmentRepository;
import jp.co.sss.crud.repository.EmployeeRepository;
import jp.co.sss.crud.util.BeanCopy;
import jp.co.sss.crud.util.Constant;

@Controller
public class RegistrationController {
	
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    /**
     * 社員情報の登録内容入力画面を出力
     *
     * @return 遷移先のビュー
     */
    @RequestMapping(path = "/regist/input", method = RequestMethod.GET)
    public String inputRegist(@ModelAttribute EmployeeForm employeeForm) {
        employeeForm.setGender(Constant.DEFAULT_GENDER);
        employeeForm.setAuthority(Constant.DEFAULT_AUTHORITY);
        employeeForm.setDeptId(Constant.DEFAULT_DEPT_ID);
        return "regist/regist_input";
    }

    /**
     * 社員情報の登録確認画面を出力
     *
     * @param employeeForm
     *            登録対象の社員情報
     * @param model
     *            モデル
     * @return 遷移先のビュー
     */
    @RequestMapping(path = "/regist/check", method = RequestMethod.POST)
    public String checkRegist(@Valid @ModelAttribute EmployeeForm employeeForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "regist/regist_input";
        } else {
            Department department = departmentRepository.getReferenceById(employeeForm.getDeptId());
            model.addAttribute("deptName", department.getDeptName());
            return "regist/regist_check";
        }
    }

    /**
     * 社員情報の登録完了画面を出力
     *
     * @param employeeForm
     *            登録対象の社員情報
     * @return モデル
     */
    @RequestMapping(path = "/regist/complete", method = RequestMethod.POST)
    public String completeRegist(EmployeeForm employeeForm) {
        Employee employee = BeanCopy.copyFormToEmployee(employeeForm);
        employeeRepository.save(employee);
        return "regist/regist_complete";
    }

    /**
     * 変更内容入力画面に戻る
     *
     * @param employeeForm 変更対象の社員情報
     * @return 遷移先のビュー
     */
    @RequestMapping(path = "/regist/back", method = RequestMethod.POST)
    public String backInputRegist(@ModelAttribute EmployeeForm employeeForm) {
        return "regist/regist_input";
    }
}
