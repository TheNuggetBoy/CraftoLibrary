/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database.table;

/** Reference: <a href="https://dev.mysql.com/doc/refman/5.5/en/charset-charsets.html">Offical MySQL CharSet list</a> */
public enum CharSet {
	/** Big5 Traditional Chinese (Default collation: big5_chinese_ci) */
	BIG5,
	/** DEC West European (Default collation: dec8_swedish_ci) */
	DEC8,
	/** DOS West European (Default collation: cp850_general_ci) */
	CP850,
	/** HP West European (Default collation: hp8_english_ci) */
	HP8,
	/** KOI8-R Relcom Russian (Default collation: koi8r_general_ci) */
	KOI8R,
	/** cp1252 West European (Default collation: latin1_swedish_ci) */
	LATIN1,
	/** ISO 8859-2 Central European (Default collation: latin2_general_ci) */
	LATIN2,
	/** 7bit Swedish (Default collation: swe7_swedish_ci) */
	SWE7,
	/** US ASCII (Default collation: ascii_general_ci) */
	ASCII,
	/** EUC-JP Japanese (Default collation: ujis_japanese_ci) */
	UJIS,
	/** Shift-JIS Japanese (Default collation: sjis_japanese_ci) */
	SJIS,
	/** ISO 8859-8 Hebrew (Default collation: hebrew_general_ci) */
	HEBREW,
	/** TIS620 Thai (Default collation: tis620_thai_ci) */
	TIS620,
	/** EUC-KR Korean (Default collation: euckr_korean_ci) */
	EUCKR,
	/** KOI8-U Ukrainian (Default collation: koi8u_general_ci) */
	KOI8U,
	/** GB2312 Simplified Chinese (Default collation: gb2312_chinese_ci) */
	GB2312,
	/** ISO 8859-7 Greek (Default collation: greek_general_ci) */
	GREEK,
	/** Windows Central European (Default collation: cp1250_general_ci) */
	CP1250,
	/** GBK Simplified Chinese (Default collation: gbk_chinese_ci) */
	GBK,
	/** ISO 8859-9 Turkish (Default collation: latin5_turkish_ci) */
	LATIN5,
	/** ARMSCII-8 Armenian (Default collation: armscii8_general_ci) */
	ARMSCII8,
	/** UTF-8 Unicode (Default collation: utf8_general_ci) */
	UTF8,
	/** UCS-2 Unicode (Default collation: ucs2_general_ci) */
	UCS2,
	/** DOS Russian (Default collation: cp866_general_ci) */
	CP866,
	/** DOS Kamenicky Czech-Slovak (Default collation: keybcs2_general_ci) */
	KEYBCS2,
	/** Mac Central European (Default collation: macce_general_ci) */
	MACCE,
	/** Mac West European (Default collation: macroman_general_ci) */
	MACROMAN,
	/** DOS Central European (Default collation: cp852_general_ci) */
	CP852,
	/** ISO 8859-13 Baltic (Default collation: latin7_general_ci) */
	LATIN7,
	/** UTF-8 Unicode (Default collation: utf8mb4_general_ci) */
	UTF8MB4,
	/** Windows Cyrillic (Default collation: cp1251_general_ci) */
	CP1251,
	/** UTF-16 Unicode (Default collation: utf16_general_ci) */
	UTF16,
	/** Windows Arabic (Default collation: cp1256_general_ci) */
	CP1256,
	/** Windows Baltic (Default collation: cp1257_general_ci) */
	CP1257,
	/** UTF-32 Unicode (Default collation: utf32_general_ci) */
	UTF32,
	/** Binary pseudo charset (Default collation: binary) */
	BINARY,
	/** GEOSTD8 Georgian (Default collation: geostd8_general_ci) */
	GEOSTD8,
	/** SJIS for Windows Japanese (Default collation: cp932_japanese_ci) */
	CP932,
	/** UJIS for Windows Japanese (Default collation: eucjpms_japanese_ci) */
	EUCJPMS,
}