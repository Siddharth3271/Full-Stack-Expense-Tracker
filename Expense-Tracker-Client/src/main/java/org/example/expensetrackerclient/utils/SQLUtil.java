package org.example.expensetrackerclient.utils;

import com.google.gson.*;
import javafx.scene.control.Alert;
import org.example.expensetrackerclient.Models.Transaction;
import org.example.expensetrackerclient.Models.TransactionCategory;
import org.example.expensetrackerclient.Models.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SQLUtil {
    //get
    public static User getUserByEmail(String userEmail){
        HttpURLConnection conn=null;
        try{
            conn= ApiUtil.fetchApi(
                    "/api/v1/user?email="+userEmail,
                    ApiUtil.RequestMethod.GET,
                    null
            );

            if(conn.getResponseCode()!=200){
                System.out.println("Error(getUserByEmail):"+conn.getResponseCode());
                return null;
            }

            String userDataJson=ApiUtil.readApiResponse(conn);
//            System.out.println(userDataJson);
            //pass the string to json object
            JsonObject jsonObject= JsonParser.parseString(userDataJson).getAsJsonObject();

            //extract the json data from string
            int id=jsonObject.get("id").getAsInt();
            String name=jsonObject.get("name").getAsString();
            String email=jsonObject.get("email").getAsString();
            String password=jsonObject.get("password").getAsString();
            LocalDateTime createdAt=new Gson().fromJson(jsonObject.get("created_at"),LocalDateTime.class);

            return new User(id,name,email,password,createdAt);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(conn!=null){
                conn.disconnect();
            }
        }

        return null;
    }

    public static List<TransactionCategory>getAllTransactionCategoryByUser(User user){
        List<TransactionCategory>categories=new ArrayList<>();
        HttpURLConnection conn=null;
        try{
            conn= ApiUtil.fetchApi(
                    "/api/v1/transaction-category/user/"+user.getId(),
                    ApiUtil.RequestMethod.GET,
                    null
            );

            if(conn.getResponseCode()!=200){
                System.out.println("Error(getAllTransactionCategoryByUser): "+conn.getResponseCode());
            }

            //if success
            String result=ApiUtil.readApiResponse(conn);
            JsonArray resultJsonArray=new JsonParser().parse(result).getAsJsonArray();

            for(JsonElement jsonElement:resultJsonArray){
                int categoryId=jsonElement.getAsJsonObject().get("id").getAsInt();
                String categoryName=jsonElement.getAsJsonObject().get("categoryName").getAsString();
                String categoryColor=jsonElement.getAsJsonObject().get("categoryColor").getAsString();

                categories.add(new TransactionCategory(categoryId,categoryName,categoryColor));
            }

            return categories;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(conn!=null){
                conn.disconnect();
            }
        }
        return null;
    }

    public static List<Transaction>getRecentTransactionByUserId(int userId,int startPage,int endPage,int size){
        List<Transaction>recentTransactions=new ArrayList<>();
        HttpURLConnection conn=null;
        try{
            conn= ApiUtil.fetchApi(
                    "/api/v1/transaction/recent/user/"+userId+"?startPage="+startPage+"&endPage="+endPage+"&size="+size,
                    ApiUtil.RequestMethod.GET,
                    null
            );
//            String debug = ApiUtil.readApiResponse(conn);
//            System.out.println("Received JSON response: " + debug);

            if(conn.getResponseCode()!=200){
                return null;
            }

            String results=ApiUtil.readApiResponse(conn);
            JsonArray resultJsonArray=new JsonParser().parse(results).getAsJsonArray();

            for(int i=0;i<resultJsonArray.size();i++) {
                JsonObject transactionJsonObj = resultJsonArray.get(i).getAsJsonObject();
                int transactionId = transactionJsonObj.get("id").getAsInt();

                TransactionCategory transactionCategory = null;

                if (transactionJsonObj.has("transactionCategory") &&
                        !transactionJsonObj.get("transactionCategory").isJsonNull()) {
                    JsonObject transactionCategoryJsonObj = transactionJsonObj.get("transactionCategory").getAsJsonObject();
                    int transactionCategoryId = transactionCategoryJsonObj.get("id").getAsInt();
                    String transactionCategoryName = transactionCategoryJsonObj.get("categoryName").getAsString();
                    String transactionCategoryColor = transactionCategoryJsonObj.get("categoryColor").getAsString();

                    transactionCategory = new TransactionCategory(
                            transactionCategoryId, transactionCategoryName, transactionCategoryColor
                    );
                }

                String transactionName = transactionJsonObj.get("transactionName").getAsString();
                double transactionAmount = transactionJsonObj.get("transactionAmount").getAsDouble();
                LocalDate transactionDate = LocalDate.parse(transactionJsonObj.get("transactionDate").getAsString());

                String transactionType = transactionJsonObj.get("transactionType").getAsString();

                Transaction transaction = new Transaction(
                        transactionId, transactionCategory, transactionName, transactionAmount, transactionDate, transactionType
                );
                recentTransactions.add(transaction);
            }
            return recentTransactions;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(conn!=null){
                conn.disconnect();
            }
        }

        return null;
    }

    public static List<Transaction>getAllTransactionsByUserId(int userId,int year,Integer month){
        List<Transaction>transactions=new ArrayList<>();
        HttpURLConnection conn=null;
        String apiPath="/api/v1/transaction/user/"+userId+"?year="+year;
        if(month!=null){
            apiPath+="&month="+month;
        }
        try{
            conn= ApiUtil.fetchApi(
                    apiPath,
                    ApiUtil.RequestMethod.GET,
                    null
            );
//            String debug = ApiUtil.readApiResponse(conn);
//            System.out.println("Received JSON response: " + debug);

            if(conn.getResponseCode()!=200){
                return null;
            }

            String results=ApiUtil.readApiResponse(conn);
//            System.out.println(results);
            JsonArray resultJson=new JsonParser().parse(results).getAsJsonArray();

            for(int i=0;i<resultJson.size();i++){
                JsonObject transactionJsonObject=resultJson.get(i).getAsJsonObject();
                int transactionId=transactionJsonObject.get("id").getAsInt();

                TransactionCategory transactionCategory=null;
                if(transactionJsonObject.has("transactionCategory") && !transactionJsonObject.get("transactionCategory").isJsonNull()){
                    JsonObject transactionCategoryJsonObject=transactionJsonObject.get("transactionCategory").getAsJsonObject();

                    int transactionCategoryId=transactionCategoryJsonObject.get("id").getAsInt();
                    String transactionCategoryName=transactionCategoryJsonObject.get("categoryName").getAsString();
                    String transactionCategoryColor=transactionCategoryJsonObject.get("categoryColor").getAsString();
                    transactionCategory=new TransactionCategory(
                      transactionCategoryId,transactionCategoryName,transactionCategoryColor
                    );
                }

                String transactionName=transactionJsonObject.get("transactionName").getAsString();
                double transactionAmount=transactionJsonObject.get("transactionAmount").getAsDouble();
                LocalDate transactionDate= LocalDate.parse(transactionJsonObject.get("transactionDate").getAsString());
                String transactionType=transactionJsonObject.get("transactionType").getAsString();

                Transaction transaction=new Transaction(
                        transactionId,transactionCategory,transactionName,transactionAmount,transactionDate,transactionType
                );
                transactions.add(transaction);
            }
            return transactions;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(conn!=null){
                conn.disconnect();
            }
        }

        return null;
    }

    public static List<Integer>getAllDistinctYears(int userId){
        List<Integer>distinctYears=new ArrayList<>();
        HttpURLConnection conn=null;
        try{
            conn= ApiUtil.fetchApi(
                    "/api/v1/transaction/years/"+userId,
                    ApiUtil.RequestMethod.GET,
                    null
            );
//            String debug = ApiUtil.readApiResponse(conn);
//            System.out.println("Received JSON response: " + debug);

            if(conn.getResponseCode()!=200){
                System.out.println("Error(getAllDistinctYears): "+conn.getResponseCode());
            }

            String results=ApiUtil.readApiResponse(conn);
//            System.out.println(results);
            JsonArray resultsArray=new JsonParser().parse(results).getAsJsonArray();
            for(int i=0;i<resultsArray.size();i++){
                int year=resultsArray.get(i).getAsInt();
                distinctYears.add(year);
            }
            return distinctYears;

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(conn!=null){
                conn.disconnect();
            }
        }

        return null;
    }

    //post
    public static boolean postLoginUser(String email,String password){
        //authenticate email and password
        HttpURLConnection conn=null;
        try{
            conn= ApiUtil.fetchApi(
                    "/api/v1/user/login?email="+email+"&password="+password,
                    ApiUtil.RequestMethod.POST,
                    null
            );

            if(conn.getResponseCode()!=200){
//                System.out.println("Failed to Authenticate");
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(conn!=null){
                conn.disconnect();
            }
        }

        return true;
    }

    public static boolean  postCreateUser(JsonObject userData){
        HttpURLConnection conn=null;
        try{
            conn=ApiUtil.fetchApi(
              "api/v1/user", ApiUtil.RequestMethod.POST,userData
            );

            if(conn.getResponseCode()!=200) {
                return false;  //failed to create account
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(conn!=null){
                conn.disconnect();
            }
        }
        return true;   //account is successfully created and stored in db
    }

    public static boolean postTransactionCategory(JsonObject transactionCategoryData){
        HttpURLConnection conn=null;
        try{
            conn=ApiUtil.fetchApi("/api/v1/transaction-category",ApiUtil.RequestMethod.POST,transactionCategoryData);

            if(conn.getResponseCode()!=200){
                return false;
            }

            return true;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            if(conn!=null){
                conn.disconnect();
            }
        }
        return false;
    }

    public static boolean postTransaction(JsonObject transactionData){
        HttpURLConnection conn=null;
        try{
            conn=ApiUtil.fetchApi("/api/v1/transaction",ApiUtil.RequestMethod.POST,transactionData);
            int statusCode = conn.getResponseCode();
            System.out.println("Response code: " + statusCode);
            if(conn.getResponseCode()!=200){
                return false;
            }

            return true;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            if(conn!=null){
                conn.disconnect();
            }
        }
        return false;
    }

    //update
    public static boolean putTransaction(JsonObject newTransactionData){
        HttpURLConnection conn=null;
        try{
            conn=ApiUtil.fetchApi("/api/v1/transaction",ApiUtil.RequestMethod.PUT,newTransactionData);
//            int statusCode = conn.getResponseCode();
//            System.out.println("Response code: " + statusCode);
            if(conn.getResponseCode()!=200){
                System.out.println("Error(Put transaction): "+conn.getResponseCode());
                return false;
            }

            return true;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            if(conn!=null){
                conn.disconnect();
            }
        }
        return false;
    }
    public static boolean putTransactionCategory(int categoryId, String newCategoryName,String newCategoryColor){
        HttpURLConnection conn=null;
        String encodedCategoryName= URLEncoder.encode(newCategoryName, StandardCharsets.UTF_8);
        String encodedCategoryColor= URLEncoder.encode(newCategoryColor, StandardCharsets.UTF_8);
        try{
            conn=ApiUtil.fetchApi("/api/v1/transaction-category/"+categoryId+"?newCategoryName="+encodedCategoryName+"&newCategoryColor="+encodedCategoryColor,ApiUtil.RequestMethod.PUT,null);

            if(conn.getResponseCode()!=200){
                System.out.println("Error(putTransactionCategory):"+conn.getResponseCode());
                return false;
            }

            return true;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            if(conn!=null){
                conn.disconnect();
            }
        }
        return false;
    }

    //delete
    public static boolean deleteTransactionCategoryById(int categoryId){
        HttpURLConnection conn=null;
        try{
            conn=ApiUtil.fetchApi("/api/v1/transaction-category/"+categoryId,ApiUtil.RequestMethod.DELETE,null);

            if(conn.getResponseCode()!=200){
                System.out.println("Error(deleteTransactionCategoryById):"+conn.getResponseCode());
                return false;
            }

            return true;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            if(conn!=null){
                conn.disconnect();
            }
        }
        return false;
    }

    public static boolean deleteTransactionById(int transactionId){
        HttpURLConnection conn=null;
        try{
            conn=ApiUtil.fetchApi("/api/v1/transaction/"+transactionId,ApiUtil.RequestMethod.DELETE,null);

            if(conn.getResponseCode()!=200){
                System.out.println("Error(deleteTransactionById):"+conn.getResponseCode());
                return false;
            }

            return true;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            if(conn!=null){
                conn.disconnect();
            }
        }
        return false;
    }

}
