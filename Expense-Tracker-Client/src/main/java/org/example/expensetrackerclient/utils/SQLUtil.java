package org.example.expensetrackerclient.utils;

import com.google.gson.*;
import javafx.scene.control.Alert;
import org.example.expensetrackerclient.Models.TransactionCategory;
import org.example.expensetrackerclient.Models.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    //update
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
}
