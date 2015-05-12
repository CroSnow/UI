package fer.unizg.ui.lab2.resolverPack;
/*
 * 
 * Test for basic functions.
 */
public class Test {

	public static void main(String[] args) {
		KnowledgeBase base=new KnowledgeBase();

		base.addKlauzul("BvD");
		base.addKlauzul("-C");
		base.addKlauzul("-D");
		base.addKlauzul("Av-B");
		base.addResolvant("-AvC");
		Klauzula a=new Klauzula("A");
		Klauzula notA=new Klauzula("-A");

		System.out.println(base.resolve());

	}

}
