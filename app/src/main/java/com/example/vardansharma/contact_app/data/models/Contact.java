package com.example.vardansharma.contact_app.data.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

// Won't require so much using data classes in kotlin
public class Contact implements Parcelable {

    private int id;
    @Json (name = "first_name")
    private String firstName;
    @Json (name = "last_name")
    private String lastName;
    @Json (name = "profile pic")
    private String profilePic;
    private boolean favorite;
    private String url;

    private String email;
    @Json (name = "phone_number")
    private String phoneNumber;

    private boolean  hasFullContactDetails;


    private Contact(Builder builder) {
        setId(builder.id);
        setFirstName(builder.firstName);
        setLastName(builder.lastName);
        setProfilePic(builder.profilePic);
        setFavorite(builder.favorite);
        setUrl(builder.url);
        email = builder.email;
        phoneNumber = builder.phoneNumber;
    }

    public boolean isHasFullContactDetails() {
        return hasFullContactDetails;
    }

    public void setHasFullContactDetails(boolean hasFullContactDetails) {
        this.hasFullContactDetails = hasFullContactDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Contact contact = (Contact) o;

        if (id != contact.id) {
            return false;
        }
        if (favorite != contact.favorite) {
            return false;
        }
        if (firstName != null ? !firstName.equals(contact.firstName) : contact.firstName != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(contact.lastName) : contact.lastName != null) {
            return false;
        }
        if (profilePic != null ? !profilePic.equals(contact.profilePic) : contact.profilePic != null) {
            return false;
        }
        if (url != null ? !url.equals(contact.url) : contact.url != null) {
            return false;
        }
        if (email != null ? !email.equals(contact.email) : contact.email != null) {
            return false;
        }
        return phoneNumber != null ? phoneNumber.equals(contact.phoneNumber) : contact.phoneNumber == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (profilePic != null ? profilePic.hashCode() : 0);
        result = 31 * result + (favorite ? 1 : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    public static final class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private String profilePic;
        private boolean favorite;
        private String url;
        private String email;
        private String phoneNumber;

        public Builder() {
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        public Builder profilePic(String val) {
            profilePic = val;
            return this;
        }

        public Builder favorite(boolean val) {
            favorite = val;
            return this;
        }

        public Builder url(String val) {
            url = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder phoneNumber(String val) {
            phoneNumber = val;
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.profilePic);
        dest.writeByte(this.favorite ? (byte) 1 : (byte) 0);
        dest.writeString(this.url);
        dest.writeString(this.email);
        dest.writeString(this.phoneNumber);
        dest.writeByte(this.hasFullContactDetails ? (byte) 1 : (byte) 0);
    }

    protected Contact(Parcel in) {
        this.id = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.profilePic = in.readString();
        this.favorite = in.readByte() != 0;
        this.url = in.readString();
        this.email = in.readString();
        this.phoneNumber = in.readString();
        this.hasFullContactDetails = in.readByte() != 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}