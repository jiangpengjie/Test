package com.ost.jay.settings.features.language;

import android.annotation.SuppressLint;
import android.app.ActivityManagerNative;
import android.os.Bundle;
import android.os.RemoteException;
import android.preference.PreferenceFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.internal.app.LocalePicker;
import com.ost.jay.settings.R;
import com.ost.jay.settings.adapter.LanguageAdapter;
import com.ost.jay.settings.widget.FocusScrollListView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

@SuppressLint("NewApi")
public class LanguageFragment extends PreferenceFragment {
        private static String TAG = "LanguageFragment";
        private View rootView;
        private ListView listView;
	@SuppressWarnings("deprecation")

	private List<Map<String,String>> listMap;
	private LanguageAdapter languageAdapter;
	private String[] specialLocaleNames = null;
    private String[] specialLocaleCodes = null;
    private ArrayAdapter<LocalePicker.LocaleInfo> mLocales;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_language, container, false);
        listMap = new ArrayList<Map<String,String>>();
		initView();
//        initData();
		return rootView;
	}

//	@Override
//    public void onResume() {
//        super.onResume();
//        listView.requestFocus();
//    }

	private void initView() {
        listView = (ListView) rootView.findViewById(R.id.language_listview);
        ((FocusScrollListView) listView).setFocusBitmap(R.drawable.wifi_listselecter_bg);
        languageAdapter = new LanguageAdapter(getActivity());
        initData();
        languageAdapter.setData(listMap);
        listView.setAdapter(languageAdapter);
        listView.setOnItemClickListener(new OnItemClickListenerImpl());
//        listView.setOnItemSelectedListener(new OnItemSelectedListenerImpl() );
    }

	private void initData() {
		int indexTemp = -1;
		Map<String, String> map = null;

		specialLocaleNames = getResources().getStringArray(
                com.android.internal.R.array.special_locale_names);
		specialLocaleCodes = getResources().getStringArray(
                com.android.internal.R.array.special_locale_codes);

		mLocales = LocalePicker.constructAdapter(getActivity());
        mLocales.sort(new LocaleComparator());

        final String[] localeNames = new String[mLocales.getCount()];
        Locale currentLocale;
        try {
            currentLocale = ActivityManagerNative.getDefault().getConfiguration().locale;
        } catch (RemoteException e) {
            currentLocale = null;
        }

        Log.d(TAG, "localeNames.length = " + localeNames.length);

        for (int i = 0; i < localeNames.length; i++) {
            Locale target = mLocales.getItem(i).getLocale();
            localeNames[i] = getDisplayName(target, specialLocaleCodes, specialLocaleNames);

            // if this locale's label has a country, use the shortened version
            // instead
            if (mLocales.getItem(i).getLabel().contains("(")) {
                String country = target.getCountry();
                if (!TextUtils.isEmpty(country)) {
                    localeNames[i] = localeNames[i] + " (" + target.getCountry() + ")";
                }
            }

            // For some reason locales are not always first letter cased, for example for
            // in the Spanish locale.
            if (localeNames[i].length() > 0) {
                localeNames[i] = localeNames[i].substring(0, 1).toUpperCase(currentLocale) +
                        localeNames[i].substring(1);
            }
            if(target.equals(currentLocale)) {
            	indexTemp = i;
            }
            map = new HashMap<String, String>();
            map.put("item_right_text", localeNames[i]);
            listMap.add(map);
        }
        if(indexTemp == -1) {
        	languageAdapter.notifyDataSetChanged();

        }else {
        	languageAdapter.setDataSelectChanged(indexTemp);
        }
	}


	class OnItemClickListenerImpl implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Log.d(TAG, "onItemClick position = " + position);
			languageAdapter.setDataSelectChanged(position);
            setLanguagePreference(position);

			//setWifiCountryCode();
			getActivity().finish();
//            getActivity().recreate();

		}
	}

//    class OnItemSelectedListenerImpl implements AdapterView.OnItemSelectedListener{
//
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            languageAdapter.setDataSelectChanged(position);
//            setLanguagePreference(position);
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//
//        }
//    }


    private static class LocaleComparator implements Comparator<LocalePicker.LocaleInfo> {
        @Override
        public int compare(LocalePicker.LocaleInfo l, LocalePicker.LocaleInfo r) {
            Locale lhs = l.getLocale();
            Locale rhs = r.getLocale();

            String lhsCountry = "";
            String rhsCountry = "";

            try {
                lhsCountry = lhs.getISO3Country();
            } catch (MissingResourceException e) {
                Log.e(TAG, "LocaleComparator cuaught exception, country set to empty.");
            }

            try {
                rhsCountry = rhs.getISO3Country();
            } catch (MissingResourceException e) {
                Log.e(TAG, "LocaleComparator cuaught exception, country set to empty.");
            }

            String lhsLang = "";
            String rhsLang = "";

            try {
                lhsLang = lhs.getISO3Language();
            } catch (MissingResourceException e) {
                Log.e(TAG, "LocaleComparator cuaught exception, language set to empty.");
            }

            try {
                rhsLang = rhs.getISO3Language();
            } catch (MissingResourceException e) {
                Log.e(TAG, "LocaleComparator cuaught exception, language set to empty.");
            }

            // if they're the same locale, return 0
            if (lhsCountry.equals(rhsCountry) && lhsLang.equals(rhsLang)) {
                return 0;
            }

            // prioritize US over other countries
            if ("USA".equals(lhsCountry)) {
                // if right hand side is not also USA, left hand side is first
                if (!"USA".equals(rhsCountry)) {
                    return -1;
                } else {
                    // if one of the languages is english we want to prioritize
                    // it, otherwise we don't care, just alphabetize
                    if ("ENG".equals(lhsLang) && "ENG".equals(rhsLang)) {
                        return 0;
                    } else {
                        return "ENG".equals(lhsLang) ? -1 : 1;
                    }
                }
            } else if ("USA".equals(rhsCountry)) {
                // right-hand side is the US and the other isn't, return greater than 1
                return 1;
            } else {
                // neither side is the US, sort based on display language name
                // then country name
                int langEquiv = lhs.getDisplayLanguage(lhs).compareTo(rhs.getDisplayLanguage(rhs));
                if (langEquiv == 0) {
                    return lhs.getDisplayCountry(lhs).compareTo(rhs.getDisplayCountry(rhs));
                } else {
                    return langEquiv;
                }
            }
        }
    }

    private static String getDisplayName(
            Locale l, String[] specialLocaleCodes, String[] specialLocaleNames) {
        String code = l.toString();

        for (int i = 0; i < specialLocaleCodes.length; i++) {
            if (specialLocaleCodes[i].equals(code)) {
                return specialLocaleNames[i];
            }
        }

        return l.getDisplayName(l);
    }

    private void setLanguagePreference(int offset) {
       LocalePicker.updateLocale(mLocales.getItem(offset).getLocale());
//        try {
//			IActivityManager am = ActivityManagerNative.getDefault();
//			Configuration config = am.getConfiguration();
//
//			config.locale = mLocales.getItem(offset).getLocale();
//
//
//			// indicate this isn't some passing default - the user wants this remembered
//			config.userSetLocale = true;
//
//			am.updateConfiguration(config);
//			// Trigger the dirty bit for the Settings Provider.
//			BackupManager.dataChanged("com.android.providers.settings");
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }



//    private void setWifiCountryCode() {
//        String countryCode = Locale.getDefault().getCountry();
//        WifiManager wifiMgr = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
//
//        if (wifiMgr != null && !TextUtils.isEmpty(countryCode)) {
//            wifiMgr.setCountryCode(countryCode, true);
//        }
//    }
}
