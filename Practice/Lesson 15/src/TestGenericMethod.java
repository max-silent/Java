import java.util.ArrayList;

public class TestGenericMethod {
	public static void main(String[] args) {

		ArrayList<RetiredEmployee> retired_list = new ArrayList<RetiredEmployee>();
		ArrayList<Employee> employee_list = new ArrayList<Employee>();
		RetiredEmployee retired1 = new RetiredEmployee("Someone Retired1");
		RetiredEmployee retired2 = new RetiredEmployee("Someone Retired2");
		RetiredEmployee retired3 = new RetiredEmployee("Someone Retired3");
		//Employee empl1 = new Employee("Someone 1");
		//Employee empl2 = new Employee("Someone 2");
		//Employee empl3 = new Employee("Someone 3");
		retired_list.add(retired1);
		retired_list.add(retired2);
		retired_list.add(retired3);
		
		//Order ord1= new Order();
		//customers.add(ord1); // Compiler error
		
		// Iterate through the list customers and do something with each 
		//  element of this collection. No casting required. 
		processData(retired_list,employee_list);
        
	}

	private static void processData(ArrayList<RetiredEmployee> retired_list, ArrayList<Employee> employee_list) {
		for (Employee empl: retired_list){
			employee_list.add(empl);
		}
		for (int i=0; i<employee_list.size(); i++){
			System.out.println(employee_list.get(i));
		}
	} 

}
