package com.example.makoreandroid.api;

import android.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.makoreandroid.MyApplication;
import com.example.makoreandroid.R;
import com.example.makoreandroid.adapters.CustomListAdapter;
import com.example.makoreandroid.dao.RemoteUserDao;
import com.example.makoreandroid.entities.RemoteUser;
import com.example.makoreandroid.jsonfiles.InvitationJson;
import com.example.makoreandroid.jsonfiles.NewContactJson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    public ContactsAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void setContactsApi(String server) {
        String[] newServer = server.split(":");
        String url;
        if(!(newServer.length == 1)) {
            if(newServer[0].equalsIgnoreCase("localhost"))
                server = "10.0.2.2:" + newServer[1];
        } else
            server = "10.0.2.2:" + server;
        this.retrofit = new Retrofit.Builder().baseUrl("http://" + server + "/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get(String token, ArrayList<RemoteUser>remote, CustomListAdapter adapter, ArrayList<RemoteUser>r, RemoteUserDao dao, String local) {
        Call<List<RemoteUser>> call = webServiceAPI.getContacts("Bearer " + token);
        call.enqueue(new Callback<List<RemoteUser>>() {
            @Override
            public void onResponse(Call<List<RemoteUser>> call, Response<List<RemoteUser>> response) {
                List<RemoteUser> remotes = response.body();
                for(RemoteUser r : remotes) {
                    r.setLocaluser(local);
                    if(r.getLastdate()!= null)
                        r.setLastdate(r.getLastdate().substring(11, 16));
                    else
                        r.setLastdate("");
                }
                remote.clear();
                remote.addAll(remotes);
                Collections.sort(remote,(o1, o2) -> o2.getLastdate().compareTo(o1.getLastdate()));
                for(RemoteUser r: remote) {
                    try {
                        dao.insert(r);
                    }catch (Exception e) {
                        dao.update(r);
                    }
                }
                r.clear();
                r.addAll(remote);
                adapter.setAdapter(remote);
            }

            @Override
            public void onFailure(Call<List<RemoteUser>> call, Throwable t) {
            }
        });
    }

    public void validation(String token, EditText RemoteName, EditText server, TextView error, String []display,
                           ArrayList<RemoteUser>remote, EditText nickName, CustomListAdapter adapter,
                           AppCompatActivity activity, AlertDialog dialog, String userName, ArrayList<RemoteUser>r, RemoteUserDao dao) {
        Call<String>call = webServiceAPI.doValidation(RemoteName.getText().toString(), server.getText().toString(), "Bearer " + token);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.raw().code() == 200) {
                    if(response.body().equals("2"))
                        error.setText(display[1]);
                    else if(response.body().equals("3"))
                        error.setText(display[0]);
                    else if(response.body().equals("4"))
                        error.setText(display[2]);
                    else if(response.body().equals("1")) {
                        String name = nickName.getText().toString();
                        if(name.equals(""))
                            name = RemoteName.getText().toString();
                        addNewContact(token ,RemoteName, nickName, server, remote,
                                adapter, activity, dialog, name, r, dao, userName);
                    } else {
                        error.setText(display[8]);
                        String name = nickName.getText().toString();
                        if(name.equals(""))
                            name = RemoteName.getText().toString();
                        sendInvitation(token ,RemoteName, nickName ,server, remote, adapter,
                                activity, dialog, name, userName, error, display, r, dao);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("coral", "coral");
            }
        });

    }
    public void addNewContact(String  token, EditText RemoteName,EditText nickName ,EditText server
                              ,ArrayList<RemoteUser>remote, CustomListAdapter adapter,
                              AppCompatActivity activity, AlertDialog dialog, String name, ArrayList<RemoteUser>r, RemoteUserDao dao, String userName) {
        NewContactJson contactJson = new NewContactJson(RemoteName.getText().toString(),
                nickName.getText().toString(), server.getText().toString());
        Call<Void> call = webServiceAPI.addNewContact("Bearer " + token, contactJson);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                RemoteUser newR = new RemoteUser(name, RemoteName.getText().toString(), "", "", server.getText().toString());
                newR.setLocaluser(userName);
                dao.insert(newR);
                remote.add(newR);
                r.clear();
                r.addAll(remote);
                adapter.setAdapter(remote);
                Toast.makeText(activity.getBaseContext(), "New Contact has been added",
                        Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                RemoteName.setText("");
                server.setText("");
                nickName.setText("");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void sendInvitation(String token, EditText RemoteName, EditText nickName ,EditText server,
                               ArrayList<RemoteUser>remote, CustomListAdapter adapter,
                               AppCompatActivity activity, AlertDialog dialog, String name, String userName,
                               TextView error, String []display, ArrayList<RemoteUser>r, RemoteUserDao dao) {
        InvitationJson invitation = new InvitationJson(userName, RemoteName.getText().toString(), "localhost:5018");
        setContactsApi(server.getText().toString());
        Call<Void>call = webServiceAPI.invitation(invitation);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                setContactsApi("localhost:5018");
                addNewContact(token ,RemoteName, nickName, server, remote,
                        adapter, activity, dialog, name, r, dao, userName);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                error.setText(display[7]);
                setContactsApi("localhost:5018");
            }
        });
    }
}
