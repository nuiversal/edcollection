package com.winway.android.sensor.nfc;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.Locale;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * 处理设备读取到的NFC信息，子类Activity继续NFCActivity，并设置OnNFCListener监听器，就可以获得处理NFC信息的功能
 * <code>public class ChildActivity extends NFCActivity{}</code>
 * 
 * @author mr-lao
 *
 */
public class NFCActivity extends FragmentActivity {

	boolean isNFCEnable = true;
	boolean hasNFCModle = true;

	public interface OnNFCListener {
		void setId(byte[] id);

		void setHex(String hex);

		void setDec(long dec);

		void setReversed(long rev);
	}

	private OnNFCListener nfcListener = null;

	public void setOnNFCListener(OnNFCListener listener) {
		this.nfcListener = listener;
	}

	private NfcAdapter mAdapter;
	private PendingIntent mPendingIntent;
	private NdefMessage mNdefPushMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			resolveIntent(getIntent());
			mAdapter = NfcAdapter.getDefaultAdapter(this);
			mPendingIntent = PendingIntent.getActivity(this, 0,
					new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
			mNdefPushMessage = new NdefMessage(new NdefRecord[] { newTextRecord("", Locale.ENGLISH, true) });
		} catch (Exception e) {

		}
	}

	void toastMessage(String text) {
		Toast.makeText(this, text, 0).show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			if (mAdapter == null) {
				// toastMessage("此设备无NFC功能");
				hasNFCModle = false;
				return;
			}
			if (!mAdapter.isEnabled()) {
				toastMessage("请打开NFC功能");
				isNFCEnable = false;
				try {
					Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
					startActivity(intent);
				} catch (Exception e) {

				}
				return;
			}
			if (mAdapter != null) {
				mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
				mAdapter.enableForegroundNdefPush(this, mNdefPushMessage);
			}
			isNFCEnable = true;
		} catch (Exception e) {
			isNFCEnable = false;
			hasNFCModle = false;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		try {
			if (mAdapter != null) {
				mAdapter.disableForegroundDispatch(this);
				mAdapter.disableForegroundNdefPush(this);
			}
		} catch (Exception e) {

		}
	}

	//
	private String hexString = "0123456789ABCDEF";

	public String decode(String bytes) {
		if (bytes.length() != 30) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		//
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	private NdefRecord newTextRecord(String text, Locale locale, boolean encodeInUtf8) {
		byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
		Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
		byte[] textBytes = text.getBytes(utfEncoding);
		int utfBit = encodeInUtf8 ? 0 : (1 << 7);
		char status = (char) (utfBit + langBytes.length);
		byte[] data = new byte[1 + langBytes.length + textBytes.length];
		data[0] = (byte) status;
		System.arraycopy(langBytes, 0, data, 1, langBytes.length);
		System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
		return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
	}

	//
	private void resolveIntent(Intent intent) {
		String action = intent.getAction();
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
				|| NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
			Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			NdefMessage[] msgs;
			if (rawMsgs != null) {
				msgs = new NdefMessage[rawMsgs.length];
				for (int i = 0; i < rawMsgs.length; i++) {
					msgs[i] = (NdefMessage) rawMsgs[i];
				}
			} else {
				byte[] empty = new byte[0];
				byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
				Parcelable tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				byte[] payload = dumpTagData(tag).getBytes();
				NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);
				NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
				msgs = new NdefMessage[] { msg };
			}

			if (null == msgs || msgs.length == 0) {
				return;
			}
			NdefRecord ndefRecord = msgs[0].getRecords()[0];
			if (null != nfcListener) {
				nfcListener.setId(ndefRecord.getId());
				nfcListener.setHex(getHex(ndefRecord.getId()) + "");
				nfcListener.setDec(getDec(ndefRecord.getId()));
				nfcListener.setReversed(getReversed(ndefRecord.getId()));
			}
		}
	}

	private String dumpTagData(Parcelable p) {
		StringBuilder sb = new StringBuilder();
		Tag tag = (Tag) p;
		byte[] id = tag.getId();
		sb.append("Tag ID (hex): ").append(getHex(id)).append("\n");
		sb.append("Tag ID (dec): ").append(getDec(id)).append("\n");
		sb.append("ID (reversed): ").append(getReversed(id)).append("\n");

		String prefix = "android.nfc.tech.";
		sb.append("Technologies: ");
		for (String tech : tag.getTechList()) {
			sb.append(tech.substring(prefix.length()));
			sb.append(", ");
		}
		sb.delete(sb.length() - 2, sb.length());
		for (String tech : tag.getTechList()) {
			if (tech.equals(MifareClassic.class.getName())) {
				sb.append('\n');
				MifareClassic mifareTag = MifareClassic.get(tag);
				String type = "Unknown";
				switch (mifareTag.getType()) {
				case MifareClassic.TYPE_CLASSIC:
					type = "Classic";
					break;
				case MifareClassic.TYPE_PLUS:
					type = "Plus";
					break;
				case MifareClassic.TYPE_PRO:
					type = "Pro";
					break;
				}
				sb.append("Mifare Classic type: ");
				sb.append(type);
				sb.append('\n');

				sb.append("Mifare size: ");
				sb.append(mifareTag.getSize() + " bytes");
				sb.append('\n');

				sb.append("Mifare sectors: ");
				sb.append(mifareTag.getSectorCount());
				sb.append('\n');

				sb.append("Mifare blocks: ");
				sb.append(mifareTag.getBlockCount());
			}

			if (tech.equals(MifareUltralight.class.getName())) {
				sb.append('\n');
				MifareUltralight mifareUlTag = MifareUltralight.get(tag);
				String type = "Unknown";
				switch (mifareUlTag.getType()) {
				case MifareUltralight.TYPE_ULTRALIGHT:
					type = "Ultralight";
					break;
				case MifareUltralight.TYPE_ULTRALIGHT_C:
					type = "Ultralight C";
					break;
				}
				sb.append("Mifare Ultralight type: ");
				sb.append(type);
			}
		}

		return sb.toString();
	}

	private String getHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = bytes.length - 1; i >= 0; --i) {
			int b = bytes[i] & 0xff;
			if (b < 0x10)
				sb.append('0');
			sb.append(Integer.toHexString(b));
			if (i > 0) {
				sb.append("");
			}
		}
		return sb.toString();
	}

	private long getDec(byte[] bytes) {
		long result = 0;
		long factor = 1;
		for (int i = 0; i < bytes.length; ++i) {
			long value = bytes[i] & 0xffl;
			result += value * factor;
			factor *= 256l;
		}
		return result;
	}

	private long getReversed(byte[] bytes) {
		long result = 0;
		long factor = 1;
		for (int i = bytes.length - 1; i >= 0; --i) {
			long value = bytes[i] & 0xffl;
			result += value * factor;
			factor *= 256l;
		}
		return result;
	}

	@Override
	public void onNewIntent(Intent intent) {
		setIntent(intent);
		resolveIntent(intent);
	}
}
