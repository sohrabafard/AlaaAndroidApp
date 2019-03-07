package ir.sanatisharif.android.konkur96.api;

import com.google.gson.Gson;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.model.DataCourse;
import ir.sanatisharif.android.konkur96.model.MainItem;

/**
 * Created by Mohamad on 2/4/2019.
 */

public class JsonParser {

    private static Gson gson = new Gson();

    public static ArrayList<MainItem> mainPagerParser(String result) {

        ArrayList<MainItem> items = new ArrayList<>();
//
//
//        try {
//            JSONObject root = new JSONObject(result);
//
//            //slider
//            JSONArray mainBanner = root.getJSONArray("mainBanner");
//            if (mainBanner.length() > 0) {
//                ArrayList<ViewSlider> v = new ArrayList<>();
//
//                for (int i = 0; i < mainBanner.length(); i++) {
//                    JSONObject slider = mainBanner.getJSONObject(i);
//                    ViewSlider viewSlider = new ViewSlider();
//                    viewSlider.setId(slider.getInt("id"));
//                    viewSlider.setTitle(!slider.isNull("title") ? slider.getString("title") : "");
//                    viewSlider.setShortDescription(!slider.isNull("shortDescription") ? slider.getString("shortDescription") : "");
//                    viewSlider.setOrder(slider.getInt("order"));
//                    viewSlider.setImgUrl(slider.getString("url"));
//                    viewSlider.setIntentLink(slider.getString("link"));
//                    v.add(viewSlider);
//                }
//                MainItem item = new MainItem();
//                item.setType(AppConstants.ITEM_SLIDER);
//               // item.setItems(v);
//                items.add(item);
//            }
//
//            //blocks
//            JSONArray blocks = root.getJSONObject("block").getJSONArray("data");
//
//            for (int i = 0; i < blocks.length(); i++) {
//
//                JSONObject data = blocks.getJSONObject(i);
//                JSONArray contents = data.getJSONArray("contents");
//                JSONArray sets = data.getJSONArray("sets");
//                JSONArray products = data.getJSONArray("products");
//                JSONArray banners = data.getJSONArray("banners");
//
//                //filling list
//                MainItem item = new MainItem();
//                item.setId(data.getInt("id"));
//                item.setType(data.getInt("type"));
//                item.setTitle(data.getString("title"));
//
//                if (contents.length() > 0) {
//
//                }
//                if (sets.length() > 0) {
//
//                    ArrayList<CategoryItemSet> categoryItemSetArrayList = new ArrayList<>();
//                    for (int j = 0; j < sets.length(); j++) {
//                        CategoryItemSet itemSet = new CategoryItemSet();
//                        JSONObject obj = sets.getJSONObject(j);
//                        itemSet.setId(obj.getInt("id"));
//                        itemSet.setTitle(obj.getString("name"));
//                        itemSet.setPhoto(obj.getString("photo"));
//                        itemSet.setContents_count(obj.getInt("contents_count"));
//                        itemSet.setUrl(obj.getString("url"));
//                        itemSet.setShortName(obj.getString("shortName"));
//
//                        //tags
//
//                        JSONArray tags_array = obj.getJSONObject("tags").getJSONArray("tags");
//                        Tags tags = new Tags();
//                        ArrayList<String> tags_string = new ArrayList<>();
//                        for (int t = 0; t < tags_array.length(); t++) {
//                            tags_string.add(tags_array.getString(t));
//                        }
//                        tags.setTags(tags_string);
//                        itemSet.setTags(tags);
//
//                        //Author
//                        JSONObject jsonObject_author = obj.getJSONObject("author");
//                        Author author = new Author();
//                        author.setId(jsonObject_author.getInt("id"));
//                        author.setFirstName(jsonObject_author.isNull("firstName") ? "" : jsonObject_author.getString("firstName"));
//                        author.setLastName(jsonObject_author.getString("lastName"));
//                        author.setPhoto(jsonObject_author.getString("photo"));
//                        itemSet.setAuthor(author);
//                        categoryItemSetArrayList.add(itemSet);
//
//                    }
//
//                    item.setType(AppConstants.ITEM_SET);
//                    item.setItems(categoryItemSetArrayList);
//                    items.add(item);
//                }
//                if (products.length() > 0) {
//
//                }
//                if (banners.length() > 0) {
//
//                }
//
//
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        return items;
    }

    public static DataCourse DetailCoursesParser(String result) {

        DataCourse detailsCourse = gson.fromJson(result, DataCourse.class);

        return detailsCourse != null ? detailsCourse : null;
    }
}
