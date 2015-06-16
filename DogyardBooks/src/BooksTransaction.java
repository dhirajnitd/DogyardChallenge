import java.util.*;

public class BooksTransaction {
	
	private static HashMap<String, Integer> bookPrice;
	private static HashMap<String, Integer> bookSellCount;
	
	private static HashMap<String, ArrayList<Integer>> transactionAmountsPerCustomer;
	private static HashMap<String, Integer> customerVisitCount;
	private static TreeMap<Integer, ArrayList<String>> customersPerTransactionAmount;
	
    private static TreeMap<String, Integer> bookSellCountIncReport;
    private static TreeMap<String, Integer> customerVisitCountReport;
    private static TreeMap<String, Integer> bookSellCountDesReport;
	
	public BooksTransaction()
	{
		setBookPrice(new HashMap<String, Integer>());                                 
		setBookSellCount(new HashMap<String, Integer>());                             
		
		setTransactionAmountsPerCustomer(new HashMap<String, ArrayList<Integer>>());  
		setCustomerVisitCount(new HashMap<String, Integer>());                        
		setCustomersPerTransactionAmount(new TreeMap<Integer, ArrayList<String>>(Comparator.reverseOrder()));
		
		bookSellCountIncReport = new TreeMap<String, Integer>();
		customerVisitCountReport = new TreeMap<String, Integer>();
		bookSellCountDesReport = new TreeMap<String, Integer>();
	}
    
    public void sortCustomerVisitCount()
    {
    	customerVisitCountReport = sortHashMapByValuesDec(getCustomerVisitCount());
    }
    
    public void sortBookSellCountDec()
    {
    	bookSellCountDesReport = sortHashMapByValuesDec(getBookSellCount());
    }
	
    public void sortBookSellCountInc()
    {
    	bookSellCountIncReport = sortHashMapByValuesInc(getBookSellCount());
    }
		
	public TreeMap<String, Integer> sortHashMapByValuesInc(HashMap<String, Integer> passedMap)
	{
		class ValueComparator implements Comparator<String> {

		    Map<String, Integer> base;
		    public ValueComparator(Map<String, Integer> base) {
		        this.base = base;
		    }

		    // Note: this comparator imposes orderings that are inconsistent with equals.    
		    public int compare(String a, String b) {
		        if (base.get(a) <= base.get(b)) {
		            return -1;
		        } else {
		            return 1;
		        }
		    }
		}
		
        ValueComparator bvc = new ValueComparator(passedMap);
        TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc);
        sorted_map.putAll(passedMap);		
		return sorted_map;
	}

	
	
	public TreeMap<String,Integer> sortHashMapByValuesDec(HashMap<String, Integer> passedMap)
	{

		class ValueComparator implements Comparator<String> {

		    Map<String, Integer> base;
		    public ValueComparator(Map<String, Integer> base) {
		        this.base = base;
		    }

		    // Note: this comparator imposes orderings that are inconsistent with equals.    
		    public int compare(String a, String b) {
		        if (base.get(a) >= base.get(b)) {
		            return -1;
		        } else {
		            return 1;
		        }
		    }
		}
		
        ValueComparator bvc = new ValueComparator(passedMap);
        TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc);
        sorted_map.putAll(passedMap);		
		return sorted_map;
	}	
	
	public void generateReport(int N)
	{
		
		reportDisplayFunction(N, customerVisitCountReport);
	   
	    System.out.print("\n");
	    
	    int n=N;
	    Iterator it = customersPerTransactionAmount.entrySet().iterator();
	    while (it.hasNext() && n>0)
	    {
	        Map.Entry pair = (Map.Entry)it.next();
	        ArrayList<String> customers = (ArrayList<String>) pair.getValue();
	        int length = customers.size();
	        for(int i = 0; i<length; i++)
	        {
	            System.out.print(customers.get(i) + " " + pair.getKey() + ",");
	        }
	        it.remove();
	        n--;
	    }	    
	    
	    System.out.print("\n");
	    
	    reportDisplayFunction(N, bookSellCountDesReport);
	    
	    System.out.print("\n");
	    
	    reportDisplayFunction(N, bookSellCountIncReport);
	}

	private void reportDisplayFunction(int N, TreeMap<String, Integer> map) {
		
		int n=N;
		int lastElement=-1;
	    Iterator it = map.entrySet().iterator();
	    while (it.hasNext() && n>=0)
	    {
	    	
	            Map.Entry pair = (Map.Entry)it.next();
	            if((Integer)pair.getValue()!=lastElement){
	            	if(n==0) break;
	            	n--;	
	            }
		        System.out.print(pair.getKey() + " " + pair.getValue() + ",");
		        lastElement = (Integer)pair.getValue();
		        it.remove();
	    }
	}

	public static HashMap<String, Integer> getBookSellCount() {
		return bookSellCount;
	}

	public static void setBookSellCount(HashMap<String, Integer> bookSellCount) {
		BooksTransaction.bookSellCount = bookSellCount;
	}

	public static HashMap<String, Integer> getBookPrice() {
		return bookPrice;
	}

	public static void setBookPrice(HashMap<String, Integer> bookPrice) {
		BooksTransaction.bookPrice = bookPrice;
	}

	public static HashMap<String, ArrayList<Integer>> getTransactionAmountsPerCustomer() {
		return transactionAmountsPerCustomer;
	}

	public static void setTransactionAmountsPerCustomer(
			HashMap<String, ArrayList<Integer>> transactionAmountsPerCustomer) {
		BooksTransaction.transactionAmountsPerCustomer = transactionAmountsPerCustomer;
	}

	public static HashMap<String, Integer> getCustomerVisitCount() {
		return customerVisitCount;
	}

	public static void setCustomerVisitCount(HashMap<String, Integer> customerVisitCount) {
		BooksTransaction.customerVisitCount = customerVisitCount;
	}

	public static TreeMap<Integer, ArrayList<String>> getCustomersPerTransactionAmount() {
		return customersPerTransactionAmount;
	}

	public static void setCustomersPerTransactionAmount(
			TreeMap<Integer, ArrayList<String>> customersPerTransactionAmount) {
		BooksTransaction.customersPerTransactionAmount = customersPerTransactionAmount;
	}
	
	public void populateBookSellCount()
	{
		completeBookSellCountHashMap(bookPrice, bookSellCount);
	}
	
	public void completeBookSellCountHashMap(HashMap<String, Integer> booklist, HashMap<String, Integer> bookCount)
	{
		Iterator it = booklist.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        if(!bookCount.containsKey((String)pair.getKey()))
	        	bookCount.put((String)pair.getKey(), 0);
	    }
	}
	
	public void checkDiscount(int amount, String customer)
	{
		ArrayList<Integer> array = new ArrayList<Integer>();
		int custAmount = 0;
		if(transactionAmountsPerCustomer.containsKey(customer))
		{
			array = transactionAmountsPerCustomer.get(customer);
			for(int i=0;i<array.size();i++)
				custAmount = custAmount + array.get(i);
		     
			if(custAmount>amount) System.out.print("1");
			else System.out.print("0");
		}
	}
	

}
