package jp.co.sss.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.crud.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Employee findByEmpIdAndEmpPass(int empId, String empPass);

	/** 社員一覧(社員IDで並び替え) */
	List<Employee> findAllByOrderByEmpId();

	/** 社員名検索(社員IDで並び替え) */
	List<Employee> findByEmpNameContainingOrderByEmpId(String empName);
}
