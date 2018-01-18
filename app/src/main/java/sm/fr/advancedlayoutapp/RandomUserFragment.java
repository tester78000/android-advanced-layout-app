package sm.fr.advancedlayoutapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sm.fr.advancedlayoutapp.model.StationMetro;

/**
 * A simple {@link Fragment} subclass.
 */
public class RandomUserFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {



    private List<StationMetro> metroList;
    private ListView metroListView;

    private int selectedIndex;

    private CustomAdapter adapter;

    public RandomUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDataFromHttp();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_random_user, container, false);

        metroListView = view.findViewById(R.id.randomUserListView);

        metroListView.setOnItemClickListener(this);

        //metroListView.setOnItemSelectedListener(this);

        return view;
    }

    private void processResponse(String response) {

        //InputStream dataRaw = getResources().openRawResource(R.raw.data);

        //Transformation de la réponse json en list de _RandomUser

        //  metroList = responseToList(String.valueOf( dataRaw));

        metroList = responseToList(response);

       /*
       //Conversion de la liste de _RandomUser en un tableau de String comportant
        //uniquement le nom des utilisateurs
        String[] data = new String[metroList.size()];

        StationMetro stm = new StationMetro();
        List<StationMetro> stmList = new ArrayList<>();
        for(int i =0; i < metroList.size(); i++){
            stm.setInsee(metroList.get(i).getInsee());
            stm.setId(metroList.get(i).getId());
            stm.setLigne(metroList.get(i).getLigne());
            stm.setVille(metroList.get(i).getVille());
            stm.setNom(metroList.get(i).getNom());
            stmList.add(stm);
            data[i] = metroList.get(i).getNom();
        }
        //Définition d'un ArrayAdapter pour alimenter la ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this.getActivity(),
                android.R.layout.simple_list_item_1,
                data
        );
        */
        adapter = new CustomAdapter(
                this.getActivity(),
                R.layout.details,
                metroList
        );

        metroListView.setAdapter(adapter);

    }

    private void getDataFromHttp() {
        String url = "http://192.168.23.110/farid/data.json";
        String url1 = "http://192.168.23.110/farid/data.json";

        //Définition de la requête
        StringRequest request = new StringRequest(
                //Méthode de la requête http
                Request.Method.GET,
                url,
                //Gestionnaire de succès
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("HTTP", response);
                        InputStream dataRaw = getResources().openRawResource(R.raw.data);
                        processResponse(response);
                    }
                },
                //Gestionnaire d'erreur
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("HTTP", error.getMessage());
                    }
                }
        );

        //Ajout de la requête à la file d'exécution
        Volley.newRequestQueue(this.getActivity())
                .add(request);
    }

    /**
     * Conversion d'une réponse json (chaîne de caractère)
     * en une liste de _RandomUser
     *
     * @param response
     * @return
     */
    private List<StationMetro> responseToList(String response) {
        List<StationMetro> list = new ArrayList<>();

        try {
            JSONObject data = new JSONObject(response);
            JSONArray jsonUsers = data.getJSONArray("records");

            for (int i = 0; i < jsonUsers.length(); i++) {
                JSONObject item = (JSONObject) jsonUsers.get(i);

                //Création d'un nouvel utilisateur
                StationMetro metro = new StationMetro();

                //hydratation de l'utilisateur
                metro.setNom(item.getJSONObject("fields").getString("nom_statio"));
                metro.setVille(item.getJSONObject("fields").getString("commune"));
                metro.setLigne(item.getJSONObject("fields").getString("ligne"));
                metro.setId(item.getJSONObject("fields").getString("objectid"));
                metro.setInsee(item.getJSONObject("fields").getString("insee"));

                JSONArray geo = item.getJSONObject("fields").getJSONObject("geometry").getJSONArray("coordinates");

                metro.setLatitude(geo.getDouble(1));
                metro.setLongitude(geo.getDouble(0));

                //ajout de l'utilisateur à la liste
                list.add(metro);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.selectedIndex = position;
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.selectedIndex = position;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class CustomAdapter extends ArrayAdapter<StationMetro> {

        Activity context;
        List<StationMetro> data;

        public CustomAdapter(@NonNull Activity context, int resource, @NonNull List<StationMetro> data) {

            super(context, resource, data);

            this.context = context;
            this.data = data;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = this.context.getLayoutInflater();
            final View view = inflater.inflate(R.layout.details, parent, false);

            StationMetro itemData = data.get(position);

            TextView inseeTextView = view.findViewById(R.id.insee);
            TextView ligneTextView = view.findViewById(R.id.ligne);
            TextView metroIdTextView = view.findViewById(R.id.metroId);
            TextView stationTextView = view.findViewById(R.id.nom);
            TextView villeIdTextView = view.findViewById(R.id.ville);
            TableLayout table = view.findViewById(R.id.visible);
            ImageView map = view.findViewById(R.id.map);

            map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Récupération de l'utilisateur sur lequel on vient de cliquer
                    StationMetro selectedMetro = data.get(selectedIndex);

                    //Création d'un intention pour l'affichage de la carte
                    Intent mapIntention = new Intent(getActivity(), MapsActivity.class);

                    //Passage des paramètres
                    mapIntention.putExtra("latitude", selectedMetro.getLatitude());
                    mapIntention.putExtra("longitude", selectedMetro.getLongitude());

                    //Affichage de l'activité
                    startActivity(mapIntention);

                }
            });

            if (position == selectedIndex) {
                table.setVisibility(View.VISIBLE);
                map.setVisibility(View.VISIBLE);

                stationTextView.setText(itemData.getNom());
                villeIdTextView.setText(itemData.getVille());
                ligneTextView.setText(itemData.getLigne());
                inseeTextView.setText(itemData.getInsee());
                metroIdTextView.setText(itemData.getId());
            } else {
                stationTextView.setText(itemData.getNom());
                table.setVisibility(View.GONE);
                map.setVisibility(View.GONE);

            }

            return view;
        }
    }
}
