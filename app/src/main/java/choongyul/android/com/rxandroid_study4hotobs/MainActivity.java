package choongyul.android.com.rxandroid_study4hotobs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    TextView tv1, tv2;
    ListView list1, list2;

    List<String> data1 = new ArrayList<>();
    List<String> data2 = new ArrayList<>();
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);

        list1 = (ListView) findViewById(R.id.list1);
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data1 );
        list2 = (ListView) findViewById(R.id.list2);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data2 );


        // 발행자는 초당 한번씩의 아이템을 발행하는 로직을 갖는다
        ConnectableObservable<Long>
        Observable<String> hotObservable = Observable.create(emitter -> {
            for(int i=0 ; i<100 ; i++) {

                emitter.onNext("item = " + i);
            }
        });
        // 위에 작성한 Observable(발행자) 에서 바로 발행을 시작한다.
        hotObservable.subscribeOn(Schedulers.io()).publish(); // 이렇게 하면 구독자가 없어도 돌아간다.

        // 3초후에 첫번째 구독자를 등록한다.
        Toast.makeText(this, "첫번째 구독자 등록", Toast.LENGTH_SHORT).show();
        hotObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            data1.add(result);
                            adapter1.notifyDataSetChanged();
                        }
                );

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "두번째 구독자 등록", Toast.LENGTH_SHORT).show();

        hotObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            data2.add(result);
                            adapter2.notifyDataSetChanged();
                        }
                );
    }
}
