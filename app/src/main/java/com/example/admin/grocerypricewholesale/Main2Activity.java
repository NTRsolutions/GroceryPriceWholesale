package com.example.admin.grocerypricewholesale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class Main2Activity extends AppCompatActivity {

    ArrayList<String> items, addvalue, prices, additems;
    ListView listView;
    EditText edvalue, editem;
    ProgressBar progressBar;
    LinearLayout lladd;
    Button butsubmit;
    String personname;
    DatabaseReference myRef;
    TextView tvempty, tvtitle;
    CustomListViewAdapter adapter;
    GoogleSignInAccount acct;
    FirebaseDatabase database;
    String gridviewitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvempty = (TextView) findViewById(R.id.tvempty);
        tvtitle = (TextView) findViewById(R.id.tvtitle);
        lladd = (LinearLayout) findViewById(R.id.lladd);
        butsubmit = (Button) findViewById(R.id.btnsubmit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.lv);


        acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personname = acct.getDisplayName();
        }

        database = FirebaseDatabase.getInstance();

        items = new ArrayList<String>();
        prices = new ArrayList<String>();
        additems = new ArrayList<String>();
        addvalue = new ArrayList<String>();

        gridviewitem = getIntent().getExtras().getString("GridViewItem");

        tvtitle.setText(gridviewitem);

        if (gridviewitem.equals("Dry Fruits")) {
            myRef = database.getInstance().getReference().child("Users").child(personname).child("DryFruits");

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        items.add(dsp.getKey().toString());
                        prices.add(dsp.getValue().toString());
                    }

                    if (items.size() == 0) {
                        tvempty.setText("No items");
                    } else {
                        tvempty.setText("");
                    }
                    progressBar.setVisibility(View.GONE);
                    listView.setAdapter(new CustomListViewAdapter(getApplicationContext(), items, prices));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(Main2Activity.this, "Database Error", Toast.LENGTH_SHORT).show();
                }
            });

            lladd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                    View v = getLayoutInflater().inflate(R.layout.customdialogbox, null);
                    builder.setView(v);

                    editem = (EditText) v.findViewById(R.id.editem);
                    edvalue = (EditText) v.findViewById(R.id.edvalue);
                    Button btnadd = (Button) v.findViewById(R.id.btnadd);
                    Button btncancel = (Button) v.findViewById(R.id.btncancel);

                    builder.setTitle("Add new item");

                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    btnadd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            additems.add(editem.getText().toString());
                            addvalue.add(edvalue.getText().toString());
                            if (additems.size() != 0) {
                                for (int i = 0; i < additems.size(); i++) {
                                    myRef.child(additems.get(i)).setValue(addvalue.get(i));
                                }
                            }
                            tvempty.setText("");
                            items.clear();
                            prices.clear();

                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                        items.add(dsp.getKey().toString());
                                        prices.add(dsp.getValue().toString());
                                    }

                                    if (items.size() == 0) {
                                        tvempty.setText("No items");
                                    } else {
                                        tvempty.setText("");
                                    }
                                    progressBar.setVisibility(View.GONE);
                                    listView.setAdapter(new CustomListViewAdapter(getApplicationContext(), items, prices));
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(Main2Activity.this, "Database Error", Toast.LENGTH_SHORT).show();
                                }
                            });

                            dialog.dismiss();
                        }
                    });

                    btncancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                }
            });

            butsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                    builder.setIcon(R.drawable.ic_done_black_24dp);
                    builder.setTitle("Data Submitted Successfully");
                    builder.setMessage(" ");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);
                }
            });


        } else if (gridviewitem.equals("Pulses")) {
            myRef = database.getInstance().getReference().child("Users").child(personname).child("Pulses");

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        items.add(dsp.getKey().toString());
                        prices.add(dsp.getValue().toString());
                    }

                    if (items.size() == 0) {
                        tvempty.setText("No items");
                    } else {
                        tvempty.setText("");
                    }
                    progressBar.setVisibility(View.GONE);
                    listView.setAdapter(new CustomListViewAdapter(getApplicationContext(), items, prices));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(Main2Activity.this, "Database Error", Toast.LENGTH_SHORT).show();
                }
            });

            lladd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                    View v = getLayoutInflater().inflate(R.layout.customdialogbox, null);
                    builder.setView(v);

                    editem = (EditText) v.findViewById(R.id.editem);
                    edvalue = (EditText) v.findViewById(R.id.edvalue);
                    Button btnadd = (Button) v.findViewById(R.id.btnadd);
                    Button btncancel = (Button) v.findViewById(R.id.btncancel);

                    builder.setTitle("Add new item");

                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    btnadd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            additems.add(editem.getText().toString());
                            addvalue.add(edvalue.getText().toString());
                            if (additems.size() != 0) {
                                for (int i = 0; i < additems.size(); i++) {
                                    myRef.child(additems.get(i)).setValue(addvalue.get(i));
                                }
                            }
                            tvempty.setText("");
                            items.clear();
                            prices.clear();
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                        items.add(dsp.getKey().toString());
                                        prices.add(dsp.getValue().toString());
                                    }

                                    if (items.size() == 0) {
                                        tvempty.setText("No items");
                                    } else {
                                        tvempty.setText("");
                                    }
                                    progressBar.setVisibility(View.GONE);
                                    listView.setAdapter(new CustomListViewAdapter(getApplicationContext(), items, prices));
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(Main2Activity.this, "Database Error", Toast.LENGTH_SHORT).show();
                                }
                            });

                            dialog.dismiss();
                        }
                    });

                    btncancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                }
            });

            butsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                    builder.setIcon(R.drawable.ic_done_black_24dp);
                    builder.setTitle("Data Submitted Successfully");
                    builder.setMessage(" ");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);
                }
            });
        } else if (gridviewitem.equals("Spices")) {
            myRef = database.getInstance().getReference().child("Users").child(personname).child("Pulses");

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        items.add(dsp.getKey().toString());
                        prices.add(dsp.getValue().toString());
                    }

                    if (items.size() == 0) {
                        tvempty.setText("No items");
                    } else {
                        tvempty.setText("");
                    }
                    progressBar.setVisibility(View.GONE);
                    listView.setAdapter(new CustomListViewAdapter(getApplicationContext(), items, prices));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(Main2Activity.this, "Database Error", Toast.LENGTH_SHORT).show();
                }
            });

            lladd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                    View v = getLayoutInflater().inflate(R.layout.customdialogbox, null);
                    builder.setView(v);

                    editem = (EditText) v.findViewById(R.id.editem);
                    edvalue = (EditText) v.findViewById(R.id.edvalue);
                    Button btnadd = (Button) v.findViewById(R.id.btnadd);
                    Button btncancel = (Button) v.findViewById(R.id.btncancel);

                    builder.setTitle("Add new item");

                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    btnadd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            additems.add(editem.getText().toString());
                            addvalue.add(edvalue.getText().toString());
                            if (additems.size() != 0) {
                                for (int i = 0; i < additems.size(); i++) {
                                    myRef.child(additems.get(i)).setValue(addvalue.get(i));
                                }
                            }
                            tvempty.setText("");
                            items.clear();
                            prices.clear();

                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                        items.add(dsp.getKey().toString());
                                        prices.add(dsp.getValue().toString());
                                    }

                                    if (items.size() == 0) {
                                        tvempty.setText("No items");
                                    } else {
                                        tvempty.setText("");
                                    }
                                    progressBar.setVisibility(View.GONE);
                                    listView.setAdapter(new CustomListViewAdapter(getApplicationContext(), items, prices));
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(Main2Activity.this, "Database Error", Toast.LENGTH_SHORT).show();
                                }
                            });

                            dialog.dismiss();
                        }
                    });

                    btncancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                }
            });

            butsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                    builder.setIcon(R.drawable.ic_done_black_24dp);
                    builder.setTitle("Data Submitted Successfully");
                    builder.setMessage(" ");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);
                }
            });

        } else if (gridviewitem.equals("Extras")) {
            myRef = database.getInstance().getReference().child("Users").child(personname).child("Extras");

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        items.add(dsp.getKey().toString());
                        prices.add(dsp.getValue().toString());
                    }

                    if (items.size() == 0) {
                        tvempty.setText("No items");
                    } else {
                        tvempty.setText("");
                    }
                    progressBar.setVisibility(View.GONE);
                    listView.setAdapter(new CustomListViewAdapter(getApplicationContext(), items, prices));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(Main2Activity.this, "Database Error", Toast.LENGTH_SHORT).show();
                }
            });

            lladd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                    View v = getLayoutInflater().inflate(R.layout.customdialogbox, null);
                    builder.setView(v);

                    editem = (EditText) v.findViewById(R.id.editem);
                    edvalue = (EditText) v.findViewById(R.id.edvalue);
                    Button btnadd = (Button) v.findViewById(R.id.btnadd);
                    Button btncancel = (Button) v.findViewById(R.id.btncancel);

                    builder.setTitle("Add new item");

                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    btnadd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            additems.add(editem.getText().toString());
                            addvalue.add(edvalue.getText().toString());
                            if (additems.size() != 0) {
                                for (int i = 0; i < additems.size(); i++) {
                                    myRef.child(additems.get(i)).setValue(addvalue.get(i));
                                }
                            }
                            tvempty.setText("");
                            items.clear();
                            prices.clear();
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                        items.add(dsp.getKey().toString());
                                        prices.add(dsp.getValue().toString());
                                    }

                                    if (items.size() == 0) {
                                        tvempty.setText("No items");
                                    } else {
                                        tvempty.setText("");
                                    }
                                    progressBar.setVisibility(View.GONE);
                                    listView.setAdapter(new CustomListViewAdapter(getApplicationContext(), items, prices));
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(Main2Activity.this, "Database Error", Toast.LENGTH_SHORT).show();
                                }
                            });

                            dialog.dismiss();
                        }
                    });

                    btncancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                }
            });

            butsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                    builder.setIcon(R.drawable.ic_done_black_24dp);
                    builder.setTitle("Data Submitted Successfully");
                    builder.setMessage(" ");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);
                }
            });

        }

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(items.get(info.position));
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                View v = getLayoutInflater().inflate(R.layout.customdialogbox, null);
                builder.setView(v);

                editem = (EditText) v.findViewById(R.id.editem);
                edvalue = (EditText) v.findViewById(R.id.edvalue);
                Button btnadd = (Button) v.findViewById(R.id.btnadd);
                Button btncancel = (Button) v.findViewById(R.id.btncancel);

                builder.setTitle("Edit item");
                editem.setText(items.get(info.position));

                final AlertDialog dialog = builder.create();
                dialog.show();

                btnadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myRef.child(items.get(info.position)).setValue(edvalue.getText().toString());
                        tvempty.setText("");
                        dialog.dismiss();
                    }
                });

                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.delete:
                myRef.child(items.get(info.position)).removeValue();
                Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
                break;
        }


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                items.clear();
                prices.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    items.add(dsp.getKey().toString());
                    prices.add(dsp.getValue().toString());
                }

                if (items.size() == 0) {
                    tvempty.setText("No items");
                } else {
                    tvempty.setText("");
                }
                progressBar.setVisibility(View.GONE);
                listView.setAdapter(new CustomListViewAdapter(getApplicationContext(), items, prices));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Main2Activity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });


        return super.onContextItemSelected(item);
    }
}