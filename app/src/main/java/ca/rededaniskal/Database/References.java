package ca.rededaniskal.Database;



public enum References {
 INDICESAUTHOR{
  @Override
  public String reference() {
   return "author-index";
  }
 },
 INDICESTITLE{
  @Override
  public String reference() {
   return "title-index";
  }
 },

 ALLBOOKS{
  @Override
  public String reference(){
   return "all-books";
  }
 },

 USER {
  @Override

  public String reference() {
   return "Users";
  }
 },
 MASTERBOOK{
  @Override

  public String reference() {
   return "master-books";
  }
 },
 BOOKINSTANCE{
  @Override

  public String reference() {
   return "book-instances";
  }
 },
 BOOKREQUEST{
  @Override

  public String reference() {
   return "BorrowRequests";
  }
 },
 FEED{
  @Override

  public String reference() {
   return "home-feed";
  }
 },
 FRIENDREQUEST{
  @Override

  public String reference() {
   return "FriendRequests";
  }
 },
 BOOKEXCHANGE{
  @Override

  public String reference() {
   return "book-exchange";
  }
 },
 FORUM{
  @Override

  public String reference() {
   return "forums";
  }
 };


 public abstract String reference();

 }

