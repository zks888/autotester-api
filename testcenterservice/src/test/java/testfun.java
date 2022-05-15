import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import java.text.SimpleDateFormat;
import java.util.*;

public class testfun {
    public static void main(final String[] args) throws Exception {
        Date startDate = DateUtils.addDays(new Date(), -15);
        String startDateStr = new SimpleDateFormat("yyyy-MM-dd").format(startDate.getTime());
        HashMap<String, Integer> satiates = new HashMap<>();
        satiates.put(startDateStr, 0);
        for (int i=14; i>0; i--) {
            Date date = DateUtils.addDays(new Date(), -i);
            String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
            satiates.put(dateStr, 15-i);
        }
        System.out.println(satiates.get("2022-05-01"));
    }
}
