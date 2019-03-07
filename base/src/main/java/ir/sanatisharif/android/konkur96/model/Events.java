package ir.sanatisharif.android.konkur96.model;

import java.security.PublicKey;
import java.util.ArrayList;

/**
 * Created by Mohamad on 11/19/2018.
 */

public class Events {

    // Events used to send message from fragment to activity.
    public static class FragmentActivityMessage {
        private String message;

        public FragmentActivityMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class CloseFragment {

        private String tagFragments;

        public String getTagFragments() {
            return tagFragments;
        }

        public void setTagFragments(String tagFragments) {
            this.tagFragments = tagFragments;
        }
    }

    public static class VideoDeleted {


        private  int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

}
