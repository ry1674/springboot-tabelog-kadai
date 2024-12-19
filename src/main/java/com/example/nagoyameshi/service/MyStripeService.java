//package com.example.nagoyameshi.service;
//
//import java.util.Map;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import com.example.nagoyameshi.entity.User;
//import com.stripe.Stripe;
//import com.stripe.exception.ApiConnectionException;
//import com.stripe.exception.ApiException;
//import com.stripe.exception.AuthenticationException;
//import com.stripe.exception.InvalidRequestException;
//import com.stripe.exception.PermissionException;
//import com.stripe.exception.RateLimitException;
//import com.stripe.exception.StripeException;
//import com.stripe.model.Customer;
//import com.stripe.model.Event;
//import com.stripe.model.SetupIntent;
//import com.stripe.model.StripeObject;
//import com.stripe.model.checkout.Session;
//import com.stripe.param.CustomerUpdateParams;
//import com.stripe.param.checkout.SessionCreateParams;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@Service
//public class StripeService {
//	@Value("${stripe.api-key}")
//	private String stripeApiKey;
//
//	private final UserService userService;
//	
//	public StripeService(UserService userService) {
//		this.userService = userService;
//	}
//	
//	// セッションを作成し、Stripeに必要な情報を返す
//	public String createStripeSession(User user, HttpServletRequest httpServletRequest) {
//		Stripe.apiKey = stripeApiKey;
//		String userId = user.getId().toString();
//        String requestUrl = new String(httpServletRequest.getRequestURL());
//		
//		SessionCreateParams params =
//			SessionCreateParams.builder()
//				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
//				.addLineItem(
//					SessionCreateParams.LineItem.builder()
//						.setPrice("price_1QETkTJtCNxzDT8fZy6wlNHO")
//						.setQuantity(1L)
//						.build())
//				.setMode(SessionCreateParams.Mode.SUBSCRIPTION)
//				.setSuccessUrl(requestUrl.replace("/subscription", "/"))
//				.setCancelUrl(requestUrl.replace("/subscription", "/"))
//				.putMetadata("userId", userId)
//				.build();
//		
//		try {
//			Session session = Session.create(params);
//			return session.getId();
//			} catch (StripeException e) {
//				System.err.println("Stripe API呼び出しでエラーが発生しました: " + e.getMessage());
//				e.printStackTrace();
//				return null; // ここで "" ではなく null を明示的に返す
//			}
//	}
//  
//	
//	public void processSessionCompleted(Event event) {
//		System.out.println("StripeServiceのprocessSessionCompleted開始");
//        // EventオブジェクトからStripeObjectオブジェクトを取得する
//        Optional<StripeObject> optionalStripeObject = event.getDataObjectDeserializer().getObject();
//
//        optionalStripeObject.ifPresentOrElse(stripeObject -> {
//
//            // StripeObjectオブジェクトをSessionオブジェクトに型変換する
//            Session session = (Session)stripeObject;
//
//            String mode = session.getMode();
//            System.out.println("*----------------" + mode);
//            
//            // 詳細なセッション情報からメタデータを取り出す
//            Map<String, String> sessionMetadata = session.getMetadata();
//            // ユーザーIDを取得
//            Integer userId = Integer.valueOf(sessionMetadata.get("userId"));
//            if(userId == null) {
//            	System.out.println("null");
//            	
//            }
//            try {
//	                // Userテーブルの有料会員フラグをTrueにする
//	                userService.upgrade(userId);
//	
//	                System.out.println("有料会員へのアップグレードの登録処理が成功しました。");
//            	// クレジットカード変更の場合
//            	if (mode.equals("setup")) {
//            		// セッションから作成したsetupIntentとcustomerIDを取得
//            		String setupIntentId = session.getSetupIntent();
//            		String customerId = session.getCustomer();
//            		
//            		// setupIntentからpaymentMethodを取得
//            		SetupIntent setupIntent = SetupIntent.retrieve(setupIntentId);
//            		String paymentMethodId = setupIntent.getPaymentMethod();
//            		
//            		// カスタマーインスタンスを取得
//            		Customer customer = Customer.retrieve(customerId);
//            		
//            		// 変更前の支払い状態の確認
//            		String defaultPaymentMethodId = customer.getInvoiceSettings().getDefaultPaymentMethod();
//            		
//            		if (defaultPaymentMethodId != null) {
//            		    System.out.println("------------before: Current default payment method ID: " + defaultPaymentMethodId);
//            		} else {
//            		    System.out.println("------------before: No default payment method is set.");
//            		}
//            		
//            		// 支払い方法のデフォルトの変更
//            		CustomerUpdateParams params = CustomerUpdateParams.builder()
//            				.setInvoiceSettings(CustomerUpdateParams.InvoiceSettings.builder()
//            				        .setDefaultPaymentMethod(paymentMethodId)
//            				        .build())
//            				.build();
//            		
//            		// 変更後の支払い状態の確認
//            		Customer customerNew = customer.update(params);
//            		
//            		String defaultPaymentMethodIdNew = customerNew.getInvoiceSettings().getDefaultPaymentMethod();
//            		
//            		if (defaultPaymentMethodIdNew != null) {
//            		    System.out.println("------------after: Current default payment method ID: " + defaultPaymentMethodIdNew);
//            		} else {
//            		    System.out.println("------------after: No default payment method is set.");
//            		}
//            	}
//            	else {
//            		System.out.println("未対応のセッションモードです。");
//            	}
//
//            } catch (RateLimitException e) {
//                System.out.println("短時間のうちに過剰な回数のAPIコールが行われました。");
//            } catch (InvalidRequestException e) {
//                System.out.println("APIコールのパラメーターが誤っているか、状態が誤っているか、方法が無効でした。");
//            } catch (PermissionException e) {
//                System.out.println("このリクエストに使用されたAPIキーには必要な権限がありません。");
//            } catch (AuthenticationException e) {
//                System.out.println("Stripeは、提供された情報では認証できません。");
//            } catch (ApiConnectionException e) {
//                System.out.println("お客様のサーバーとStripeの間でネットワークの問題が発生しました。");
//            } catch (ApiException e) {
//                System.out.println("Stripe側で問題が発生しました（稀な状況です）。");
//            } catch (StripeException e) {
//                System.out.println("Stripeとの通信中に予期せぬエラーが発生しました。");
//            } catch (Exception e) {
//                System.out.println("有料会員へのアップグレードの登録処理中に予期せぬエラーが発生しました。");
//            }
//        },
//        () -> {
//            System.out.println("有料会員へのアップグレードの登録処理が失敗しました。");
//        });
//     		    
//        // StripeのAPIとstripe-javaライブラリのバージョンをコンソールに出力する
//        System.out.println("Stripe API Version: " + event.getApiVersion());
//        System.out.println("stripe-java Version: " + Stripe.VERSION + ", stripe-java API Version: " + Stripe.API_VERSION);
//    }
//	
////	カリキュラム参考。決済はできるが登録ができない。
////    public void processSessionCompleted(Event event) {
////        Optional<StripeObject> optionalStripeObject = event.getDataObjectDeserializer().getObject();
////        optionalStripeObject.ifPresentOrElse(stripeObject -> {
////            Session session = (Session)stripeObject;
////            SessionRetrieveParams params = SessionRetrieveParams.builder().addExpand("payment_intent").build();
////            
////            try {
////                session = Session.retrieve(session.getId(), params, null);
////                Map<String, String> paymentIntentObject = session.getPaymentIntentObject().getMetadata();
////        		Integer userId = Integer.valueOf(paymentIntentObject.get("userId"));
////        		System.out.println(userId);
////                userService.upgrade(paymentIntentObject);
////            } catch (StripeException e) {
////                e.printStackTrace();
////            }
////            System.out.println("有料プランの登録処理が成功しました。");
////            System.out.println("Stripe API Version: " + event.getApiVersion());
////            System.out.println("stripe-java Version: " + Stripe.VERSION);
////        },
////        () -> {
////            System.out.println("有料プランの登録処理が失敗しました。");
////            System.out.println("Stripe API Version: " + event.getApiVersion());
////            System.out.println("stripe-java Version: " + Stripe.VERSION);
////        });
////    }
//	
//}
//
