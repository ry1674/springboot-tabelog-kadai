package com.example.nagoyameshi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Subscription;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentMethodAttachParams;
import com.stripe.param.SubscriptionCreateParams;
import com.stripe.param.SubscriptionListParams;

import jakarta.annotation.PostConstruct;

@Service
public class StripeService {
	@Value("${stripe.api-key}")
	private String stripeApiKey;
	
//  依存性の注入後に一度だけ実行するメソッド
    @PostConstruct
    private void init() {
        // Stripeのシークレットキーを設定する
        Stripe.apiKey = stripeApiKey;
    }
	
//  顧客（StripeのCustomerオブジェクト）を作成する
    public Customer createCustomer(User user) throws StripeException {
//    	顧客作成時に渡すユーザーの情報
    	CustomerCreateParams customerCreateParams =
    		CustomerCreateParams.builder()
    			.setName(user.getName())
    			.setEmail(user.getEmail())
    			.build();
    	
    	return Customer.create(customerCreateParams);	
	}
    
//  支払い方法を顧客に紐づける。
    public void attachPaymentMethodToCustomer(String paymentMethodId, String customerId) throws StripeException {
//    	支払い方法のIDをデフォルトの支払い方法として設定したCustomerUpdateParamsオブジェクトを作成する。	
    	PaymentMethodAttachParams paymentMethodAttachParams = 
    		PaymentMethodAttachParams.builder()
    			.setCustomer(customerId)
    			.build();
    	
    	PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);
    	paymentMethod.attach(paymentMethodAttachParams);
    }
	
//  顧客のデフォルトの支払い方法を設定する
    public void setDefaultPaymentMethod(String paymentMethodId, String customerId) throws StripeException {
//    	支払い方法のIDをデフォルトの支払い方法としてせってしたCustomerUpdateParamsオブジェクトを作成する
    	CustomerUpdateParams customerUpdateParams =
    		CustomerUpdateParams.builder()
    			.setInvoiceSettings(
    				CustomerUpdateParams.InvoiceSettings.builder()
    					.setDefaultPaymentMethod(paymentMethodId)
    					.build()
    			)
    			.build();
    	
    	Customer customer = Customer.retrieve(customerId);
    	customer.update(customerUpdateParams);
    }
    
//  サブスクリプションを作成する。  
    public Subscription createSubscription(String customerId, String priceId) throws StripeException {
    	SubscriptionCreateParams subscriptionCreateParams = 
    		SubscriptionCreateParams.builder()
    			.setCustomer(customerId)
    			.addItem(
    				SubscriptionCreateParams
    					.Item.builder()
    					.setPrice(priceId)
    					.build()
    			)
    			.build();
    	
    	 return Subscription.create(subscriptionCreateParams);
    }
    
//  顧客のデフォルトの支払い方法を取得する。
    public PaymentMethod getDefaultPaymentMethod(String customerId) throws StripeException{
//    	顧客IDを元にCustomerオブジェクトを取得する
    	Customer customer = Customer.retrieve(customerId);
//    	Customerオブジェクトからデフォルトの支払い方法のIDを取得する。
    	String defaultPaymentMethodId = customer.getInvoiceSettings().getDefaultPaymentMethod();
//    	デフォルトの支払い方法のIDをもとにPaymentMethodオブジェクトを取得し、戻り値として返す。
    	return PaymentMethod.retrieve(defaultPaymentMethodId);
    }
    
//  顧客のデフォルトの支払い方法のIDを取得する。
    public String getDefaultPaymentMethodId(String customerId) throws StripeException{
    	Customer customer = Customer.retrieve(customerId);
    	return customer.getInvoiceSettings().getDefaultPaymentMethod();
    }
    
//  支払い方法と顧客の紐付けを解除する。
    public void detachPaymentMethodFromCustomer(String paymentMethodId) throws StripeException{
    	PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);
    	paymentMethod.detach();
    }

//  サブスクリプションを取得する。  
    public List<Subscription> getSubscriptions(String customerId) throws StripeException{
//    	顧客IDを設定したSubscriptionListParamsオブジェクトを作成する。
    	SubscriptionListParams subscriptionListParams = 
    		SubscriptionListParams.builder()
    			.setCustomer(customerId)
    			.build();
//    	Subscriptionオブジェクトのリストを取得し、戻り値として返す。
    	return Subscription.list(subscriptionListParams).getData();	
    }
    
//  サブスクリプションをキャンセルする。  
    public void cancelSubscriptions(List<Subscription> subscriptions) throws StripeException{
//    	リストの要素を全て取り出す。
    	for(Subscription cancelSubscription : subscriptions) {
    		cancelSubscription.cancel();
    	}
    }
}