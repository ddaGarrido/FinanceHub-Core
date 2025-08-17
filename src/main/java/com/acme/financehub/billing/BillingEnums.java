package com.acme.financehub.billing;
public class BillingEnums {
  public enum BillingSourceType { energy, telecom, rent, subscription, other }
  public enum AmountPolicy { variable, fixed }
  public enum BillingCycle { monthly, yearly }
  public enum BillChargeStatus { open, paid, overdue }
}
