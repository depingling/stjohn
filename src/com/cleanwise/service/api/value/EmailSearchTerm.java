package com.cleanwise.service.api.value;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.search.SearchTerm;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.Constants;

public class EmailSearchTerm extends SearchTerm {

    public static final int TERM_STARTS_WITH = 1;
    public static final int TERM_CONTAINS = 2;
    public static final int TERM_EXACT_MATCH = 3;

    public static final int TERM_AND = 1;
    public static final int TERM_OR = 2;

    private String subject;
    private String text;
    private String[] from;
    private int searchSubjectType = TERM_STARTS_WITH;
    private int searchTextType = TERM_CONTAINS;
    private int termType = TERM_AND;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getFrom() {
        return from;
    }

    public void setFrom(String[] from) {
        this.from = from;
    }

    public int getSearchTextType() {
        return searchTextType;
    }

    public void setSearchTextType(int searchTextType) {
        this.searchTextType = searchTextType;
    }

    public int getSearchSubjectType() {
        return searchSubjectType;
    }

    public void setSearchSubjectType(int searchSubjectType) {
        this.searchSubjectType = searchSubjectType;
    }

    public int getTermType() {
        return termType;
    }

    public void setTermType(int termType) {
        this.termType = termType;
    }

    public boolean match(Message message) {

        ArrayList<Boolean> matches = new ArrayList<Boolean>();

        boolean searchBySubject = false;
        boolean searchByText = false;
        boolean searchByFrom = false;

        if (this.subject != null) {
            searchBySubject = true;
        }

        if (this.text != null) {
            searchByText = true;
        }

        if (this.from != null) {
            searchByFrom = true;
        }

        try {
            if (searchBySubject) {

                Boolean match;
                String mSubject = Utility.strNN(message.getSubject());

                switch (this.searchSubjectType) {
                    case TERM_STARTS_WITH:
                        match = mSubject.toLowerCase().startsWith(this.subject.toLowerCase());
                        break;
                    case TERM_CONTAINS:
                        match = mSubject.toLowerCase().indexOf(this.subject.toLowerCase()) > -1;
                        break;
                    case TERM_EXACT_MATCH:
                        match = mSubject.equalsIgnoreCase(this.subject);
                        break;
                    default:
                        match = mSubject.toLowerCase().startsWith(this.subject.toLowerCase());
                        break;
                }
                matches.add(match);
            }

            if (searchByText) {

                Boolean match;
                String mText = Utility.strNN(getMailText(message));

                switch (this.searchTextType) {
                    case TERM_STARTS_WITH:
                        match = mText.toLowerCase().startsWith(this.text.toLowerCase());
                        break;
                    case TERM_CONTAINS:
                        match = mText.toLowerCase().indexOf(this.text.toLowerCase()) > -1;
                        break;
                    case TERM_EXACT_MATCH:
                        match = mText.equalsIgnoreCase(this.text);
                        break;
                    default:
                        match = mText.toLowerCase().startsWith(this.text.toLowerCase());
                        break;
                }
                matches.add(match);
            }

            if (searchByFrom) {

                Boolean match = false;
                Address[] mFrom = message.getFrom();
                Address[] from = convertToAddresses(this.from);

                for (Address aMFrom : mFrom) {
                    for (Address aFrom : from) {
                        if (aMFrom.equals(aFrom)) {
                            match = true;
                            break;
                        }
                    }
                    if (match) {
                        break;
                    }
                }
                matches.add(match);
            }

            switch (this.termType) {
                case TERM_AND: {
                    Boolean match = null;
                    for (Boolean aMatch : matches) {
                        match = match != null ? match && aMatch : aMatch;
                    }
                    return match;
                }
                case TERM_OR: {
                    Boolean match = null;
                    for (Boolean aMatch : matches) {
                        match = match != null ? match || aMatch : aMatch;
                    }
                    return match;
                }
                default: {
                    Boolean match = null;
                    for (Boolean aMatch : matches) {
                        match = match != null ? match && aMatch : aMatch;
                    }
                    return match;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    private String getMailText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s;
            s = (String) p.getContent();
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType(Constants.EMAIL_FORMAT_PLAIN_TEXT)) {
                    if (text == null)
                        text = getMailText(bp);
                } else if (bp.isMimeType(Constants.EMAIL_FORMAT_HTML)) {
                    String s = getMailText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getMailText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getMailText(mp.getBodyPart(i));
                if (s != null) {
                    return s;
                }
            }
        }

        return null;
    }

    private Address[] convertToAddresses(String[] pAddresses) throws javax.mail.internet.AddressException {

        if (pAddresses == null || pAddresses.length == 0) {
            return new Address[0];
        }

        Address[] addressArr = new Address[pAddresses.length];
        for (int ii = 0; ii < pAddresses.length; ii++) {
            addressArr[ii] = new InternetAddress(pAddresses[ii]);
        }

        return addressArr;
    }
}
