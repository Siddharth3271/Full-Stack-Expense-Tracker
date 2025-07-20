package org.example.expensetrackerclient.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.Alert;
import org.example.expensetrackerclient.Models.User;

import java.net.HttpURLConnection;

public class SQLUtil {
    //get
    public static User getUserByEmail(String email){
        HttpURLConnection conn=null;
        try{
            conn= ApiUtil.fetchApi(
                    "/api/v1/user?email="+email,
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

            //extract the json data
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
}
