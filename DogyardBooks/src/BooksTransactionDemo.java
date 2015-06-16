import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BooksTransactionDemo {

	public static void main(String[] args) throws IOException {
		
		BooksTransaction dogyard = new BooksTransaction();
		
        int N=2;  //parameter to generate the report
        int D=1700; //amount to be inputed
        String C="C12397"; // customer for whom the report is to be generated
        
		String priceListFile = "C:\\Users\\HP\\Desktop\\price.list";
		String transactionListFile = "C:\\Users\\HP\\Desktop\\transaction.list";
		
    	populateBookList(dogyard, priceListFile);              //processing price list
    	
		processTransactionList(dogyard, transactionListFile);  //processing transaction list
		
		dogyard.sortCustomerVisitCount();
		dogyard.sortBookSellCountDec();
		dogyard.sortBookSellCountInc();
		
		dogyard.generateReport(N);    //generating report
		
		//dogyard.checkDiscount(D, C);   // checking if discount applicable or not
	}

	private static void processTransactionList(BooksTransaction dogyard,
			String transactionListFile) throws FileNotFoundException,
			IOException {
		BufferedReader br;
		br = new BufferedReader(new FileReader(transactionListFile));
    	String line;
    	while ((line = br.readLine()) != null)
    	{
    	    String[] cols = line.split(",");
    	    int length = cols.length;
    	    int transactionAmount = 0;
    	    for(int i=1;i<length;i++)
    	    {
    	    	if(BooksTransaction.getBookSellCount().containsKey(cols[i]))
    	    	{
    	    		BooksTransaction.getBookSellCount().put(cols[i], BooksTransaction.getBookSellCount().get(cols[i]) + 1);
    	    	}
    	    	else
    	    	{
    	    		BooksTransaction.getBookSellCount().put(cols[i], 1);
    	    	}
    	    	transactionAmount = transactionAmount + BooksTransaction.getBookPrice().get(cols[i]);
    	    }
    	    
    	    populateTransactionAmountsPerCustomerHashMap(cols,transactionAmount);
    	    
    	    populateCustomerVisitCountHashMap(cols);
    	    
    	    populateCustomerPerTransactionAmountHashMap(cols, transactionAmount);
    	    
    	    dogyard.populateBookSellCount();
    	}
    	
	}

	private static void populateCustomerPerTransactionAmountHashMap(
			String[] cols, int transactionAmount) {
		if(BooksTransaction.getCustomersPerTransactionAmount().containsKey(transactionAmount))
		{
			ArrayList<String> arrayList = BooksTransaction.getCustomersPerTransactionAmount().get(transactionAmount);
			arrayList.add((String)cols[0]);
			BooksTransaction.getCustomersPerTransactionAmount().put(transactionAmount, arrayList);
		}
		else
		{
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(cols[0]);
			BooksTransaction.getCustomersPerTransactionAmount().put(transactionAmount, arrayList);    	    	
		}
	}

	private static void populateCustomerVisitCountHashMap(String[] cols) {
		if(BooksTransaction.getCustomerVisitCount().containsKey(cols[0]))
		{
			BooksTransaction.getCustomerVisitCount().put(cols[0], BooksTransaction.getCustomerVisitCount().get(cols[0]) + 1);
		}
		else
		{
			BooksTransaction.getCustomerVisitCount().put(cols[0], 1);
		}
	}

	private static void populateTransactionAmountsPerCustomerHashMap(
			String[] cols, int transactionAmount) {
		if(BooksTransaction.getTransactionAmountsPerCustomer().containsKey(cols[0]))
		{
			ArrayList<Integer> arrayList = BooksTransaction.getTransactionAmountsPerCustomer().get(cols[0]);
			arrayList.add((Integer)transactionAmount);
			BooksTransaction.getTransactionAmountsPerCustomer().put(cols[0], arrayList);
		}
		else
		{
			ArrayList<Integer> arrayList = new ArrayList<Integer>();
			arrayList.add(transactionAmount);
			BooksTransaction.getTransactionAmountsPerCustomer().put(cols[0], arrayList);
		}
	}    	    	

	private static void populateBookList(BooksTransaction dogyard,
			String priceListFile) throws FileNotFoundException, IOException {
		BufferedReader br;
		br = new BufferedReader(new FileReader(priceListFile));
    	String line;
    	while ((line = br.readLine()) != null)
    	{
    	    String[] cols = line.split(",");
    	    BooksTransaction.getBookPrice().put((String)cols[0], (Integer)Integer.parseInt(cols[1]));
    	}
	}
}