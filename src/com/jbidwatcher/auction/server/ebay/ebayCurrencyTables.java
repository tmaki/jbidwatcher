package com.jbidwatcher.auction.server.ebay;

import com.jbidwatcher.util.Currency;
import com.jbidwatcher.util.ErrorManagement;

public class ebayCurrencyTables {
  private static Currency[][] us_incrementTable = {
    { new Currency(   "$0.99"), new Currency( "$0.05") },
    { new Currency(   "$4.99"), new Currency( "$0.25") },
    { new Currency(  "$24.99"), new Currency( "$0.50") },
    { new Currency(  "$99.99"), new Currency( "$1.00") },
    { new Currency( "$249.99"), new Currency( "$2.50") },
    { new Currency( "$499.99"), new Currency( "$5.00") },
    { new Currency( "$999.99"), new Currency("$10.00") },
    { new Currency("$2499.99"), new Currency("$25.00") },
    { new Currency("$4999.99"), new Currency("$50.00") },
    { Currency.NoValue(), new Currency("$100.00") } };

  private static Currency[][] au_incrementTable = {
      { new Currency(   "AUD0.99"), new Currency( "AUD0.05") },
      { new Currency(   "AUD4.99"), new Currency( "AUD0.25") },
      { new Currency(  "AUD24.99"), new Currency( "AUD0.50") },
      { new Currency(  "AUD99.99"), new Currency( "AUD1.00") },
      { new Currency( "AUD249.99"), new Currency( "AUD2.50") },
      { new Currency( "AUD499.99"), new Currency( "AUD5.00") },
      { new Currency( "AUD999.99"), new Currency("AUD10.00") },
      { new Currency("AUD2499.99"), new Currency("AUD25.00") },
      { new Currency("AUD4999.99"), new Currency("AUD50.00") },
      { Currency.NoValue(), new Currency("AUD100.00") } };

  private static Currency[][] ca_incrementTable = {
        { new Currency( "CAD0.99"), new Currency( "CAD0.05") },
        { new Currency( "CAD4.99"), new Currency( "CAD0.25") },
        { new Currency("CAD24.99"), new Currency( "CAD0.50") },
        { new Currency("CAD99.99"), new Currency( "CAD1.00") },
        { Currency.NoValue(), new Currency("CAD2.50") } };

  private static Currency[][] uk_incrementTable = {
          { new Currency(   "GBP1.00"), new Currency( "GBP0.05") },
          { new Currency(   "GBP5.00"), new Currency( "GBP0.20") },
          { new Currency(  "GBP15.00"), new Currency( "GBP0.50") },
          { new Currency(  "GBP60.00"), new Currency( "GBP1.00") },
          { new Currency( "GBP150.00"), new Currency( "GBP2.00") },
          { new Currency( "GBP300.00"), new Currency( "GBP5.00") },
          { new Currency( "GBP600.00"), new Currency("GBP10.00") },
          { new Currency("GBP1500.00"), new Currency("GBP25.00") },
          { new Currency("GBP3000.00"), new Currency("GBP50.00") },
          { Currency.NoValue(), new Currency("GBP100.00") } };

  private static Currency[][] fr_incrementTable = {
            { new Currency(    "FRF4.99"), new Currency( "FRF0.25") },
            { new Currency(   "FRF24.99"), new Currency( "FRF0.50") },
            { new Currency(   "FRF99.99"), new Currency( "FRF1.00") },
            { new Currency(  "FRF249.99"), new Currency( "FRF2.50") },
            { new Currency(  "FRF499.99"), new Currency( "FRF5.00") },
            { new Currency(  "FRF999.99"), new Currency("FRF10.00") },
            { new Currency( "FRF2499.99"), new Currency("FRF25.00") },
            { new Currency( "FRF9999.99"), new Currency("FRF100.00") },
            { new Currency("FRF49999.99"), new Currency("FRF250.00") },
            { Currency.NoValue(), new Currency("FRF500.00") } };

  private static Currency[][] eu_incrementTable = {
              { new Currency(   "EUR49.99"), new Currency( "EUR0.50") },
              { new Currency(  "EUR499.99"), new Currency( "EUR1.00") },
              { new Currency(  "EUR999.99"), new Currency( "EUR5.00") },
              { new Currency( "EUR4999.99"), new Currency("EUR10.00") },
              { Currency.NoValue(), new Currency("EUR50.00") } };

  private static Currency[][] tw_incrementTable = {
                { new Currency(   "NTD500"), new Currency( "NTD15") },
                { new Currency(  "NTD2500"), new Currency( "NTD30") },
                { new Currency(  "NTD5000"), new Currency( "NTD50") },
                { new Currency( "NTD25000"), new Currency("NTD100") },
                { Currency.NoValue(), new Currency("NTD200") } };

  private static Currency[][] ch_incrementTable = {
                  { new Currency(   "CHF49.99"), new Currency( "CHF0.50") },
                  { new Currency(  "CHF499.99"), new Currency( "CHF1.00") },
                  { new Currency(  "CHF999.99"), new Currency( "CHF5.00") },
                  { new Currency( "CHF4999.99"), new Currency("CHF10.00") },
                  { Currency.NoValue(), new Currency("CHF50.00") } };

  private static Currency zeroDollars = new Currency("$0.00");
  private static Currency zeroPounds = new Currency("GBP 0.00");
  private static Currency zeroFrancs = new Currency("FR 0.00");
  private static Currency zeroSwissFrancs = new Currency("CHF0.00");
  private static Currency zeroEuros = new Currency("EUR 0.00");
  private static Currency zeroAustralian = new Currency("AUD0.00");
  private static Currency zeroTaiwanese = new Currency("NTD0.00");
  private static Currency zeroCanadian = new Currency("CAD0.00");

  Currency getMinimumBidIncrement(Currency currentBid, int bidCount) {
    Currency correctedValue = currentBid;
    Currency zeroIncrement = zeroDollars;
    Currency[][] rightTable;

    switch (currentBid.getCurrencyType()) {
      //  Default to USD, so we don't freak if we're passed a bad
      //  value.  We'll get the wrong answer, but we won't thrash.
      default:
        correctedValue = zeroDollars;
        rightTable = us_incrementTable;
        break;
      case Currency.US_DOLLAR:
        rightTable = us_incrementTable;
        break;
      case Currency.UK_POUND:
        rightTable = uk_incrementTable;
        zeroIncrement = zeroPounds;
        break;
      case Currency.FR_FRANC:
        rightTable = fr_incrementTable;
        zeroIncrement = zeroFrancs;
        break;
      case Currency.CH_FRANC:
        rightTable = ch_incrementTable;
        zeroIncrement = zeroSwissFrancs;
        break;
      case Currency.EURO:
        rightTable = eu_incrementTable;
        zeroIncrement = zeroEuros;
        break;
      case Currency.TW_DOLLAR:
        rightTable = tw_incrementTable;
        zeroIncrement = zeroTaiwanese;
        break;
      case Currency.CAN_DOLLAR:
        rightTable = ca_incrementTable;
        zeroIncrement = zeroCanadian;
        break;
      case Currency.AU_DOLLAR:
        rightTable = au_incrementTable;
        zeroIncrement = zeroAustralian;
        break;
    }

    if (bidCount == 0) return zeroIncrement;

    for (Currency[] aRightTable : rightTable) {
      Currency endValue = aRightTable[0];
      Currency incrementValue = aRightTable[1];

      //  Sentinel.  If we reach the end, return the max.
      if (endValue == null || endValue.isNull()) return incrementValue;

      try {
        //  If it's less than, or equal, to the end value than we use
        //  that increment amount.
        if (correctedValue.less(endValue)) return incrementValue;
        if (!endValue.less(correctedValue)) return incrementValue;
      } catch (Currency.CurrencyTypeException e) {
        /* Should never happen, since we've checked the currency already.  */
        ErrorManagement.handleException("Currency comparison threw a bad currency exception, which should be impossible.", e);
      }
    }
    return null;
  }
}