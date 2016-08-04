package asigments;

	import org.junit.runner.RunWith;
	import org.junit.runners.Suite;
	/*
	 * NAME:TSHIKOTSHI VHUTALI
	 * STD NO:TSHVHU003
	 * CSC2002S ASSIGMENT 1
	 */
	//This class runs multiple test cases
	@RunWith(Suite.class)
	@Suite.SuiteClasses({
		TestMergeSortParallel.class, 
		TestQuickSortParallel.class
	})

	public class TestSuite { }


