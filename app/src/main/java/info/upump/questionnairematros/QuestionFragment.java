package info.upump.questionnairematros;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import info.upump.questionnairematros.adapter.QuestionAdapter;
import info.upump.questionnairematros.db.DataBaseHelper;
import info.upump.questionnairematros.db.MyLoader;
import info.upump.questionnairematros.entity.Question;

/**
 * Created by explo on 09.10.2017.
 */

public class QuestionFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Question>> {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private DataBaseHelper helper;
    private QuestionAdapter questionAdapter;
    private SearchView searchView;
    private List<Question> list = new ArrayList<>();
    private EditText editText;
    public static String TAG="question";
    LinearLayoutManager linearLayoutManager;
    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg.arg1 +"------"+(String)msg.obj);
            if(msg.what==100){
                try {

                    int number = Integer.parseInt( (String) msg.obj);
                    if(number>recyclerView.getAdapter().getItemCount()){
                        number = recyclerView.getAdapter().getItemCount();
                    }
                    if(number<1){
                        number = 1;
                    }
                    System.out.println("номер строки если цифра "+number);
                    linearLayoutManager.scrollToPositionWithOffset(number-1,0);

                }catch (NumberFormatException e) {
                    System.out.println("если символ !"+msg.obj+"!");


                        questionAdapter.filter((String) msg.obj);

                }catch (IndexOutOfBoundsException e){
                    recyclerView.stopScroll();
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        View root = inflater.inflate(R.layout.fragment_list, container, false);


        questionAdapter = new QuestionAdapter(getActivity(), list);

       linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) root.findViewById(R.id.listQuestionSearch);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(questionAdapter);

        progressBar = root.findViewById(R.id.progressSearch);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        searchView = root.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                handler.removeMessages(100);
                handler.sendMessageDelayed(handler.obtainMessage(100, newText), 250);
                return true;
            }
        });



        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<List<Question>> onCreateLoader(int i, Bundle bundle) {
        System.out.println(1);

        return new MyLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<Question>> loader, List<Question> data) {
        System.out.println(2);
        System.out.println(data.size());
        list.clear();
        list.addAll(data);
        questionAdapter.notifyDataSetChanged();
        progressBar.setVisibility(progressBar.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<Question>> loader) {

    }
}
