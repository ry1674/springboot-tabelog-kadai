package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.StripeService;
import com.example.nagoyameshi.service.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Subscription;

@Controller
@RequestMapping("/subscription")
public class SubscriptionController {
	@Value("${stirpe.priceId}")
	private String priceId;
	
	private final UserService userService;
	private final StripeService stripeService;
	private final UserRepository userRepository;
	
	public SubscriptionController(UserService userService, StripeService stripeService, UserRepository userRepository) {
		this.userService = userService;
		this.stripeService = stripeService;
		this.userRepository = userRepository;
	}

//	有料会員登録ページにアクセス
	@GetMapping("/register")
	public String register() {	
		return "subscription/register";
	}
//　ログイン中のユーザを顧客として作成し、フォームから送信されたクレジットカード情報をデフォルトの支払い方法として設定する。
	@PostMapping("/create")
	public String create(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestParam String paymentMethodId, RedirectAttributes redirectAttributes) {
		User user = userDetailsImpl.getUser();
//	    	現在ログイン中のユーザーのUserエンティティの stripeCustomerIdフィールドが nullであれば、以下の処理を行う
		if(user.getStripeCustomerId() == null) {
			try {
//		    	顧客（Stripeの Customerオブジェクト）を作成する
				Customer customer = stripeService.createCustomer(user);
//				stripeCustomerIdフィールドに顧客いDを保存する。
				String stripeCustomerId = customer.getId();
				user.setStripeCustomerId(stripeCustomerId);
				userRepository.save(user);
			} catch(StripeException e) {
				redirectAttributes.addFlashAttribute("errorMessage", "有料プランへの登録が失敗しました。再度お試しください。");
				return "redirect:/";
			}	
	    }

		String stripeCustomerId = user.getStripeCustomerId();
		try {
//		    フォームから送信された支払い方法を顧客に紐づける
			stripeService.attachPaymentMethodToCustomer(paymentMethodId, stripeCustomerId);
//	    	フォームから送信された支払い方法を顧客のデフォルトの支払い方法に設定する
			stripeService.setDefaultPaymentMethod(paymentMethodId, stripeCustomerId);
//	    	サブスクリプションを作成する
	    	stripeService.createSubscription(stripeCustomerId, priceId);			
		} catch(StripeException e) {
			redirectAttributes.addFlashAttribute("errorMessage", "有料プランへの登録が失敗しました。再度お試しください。");
			return "redirect:/";
		}
//	    ユーザーのロールを更新する
		userService.updateRole(user, "ROLE_PAID");
		userService.refreshAuthenticationByRole("ROLE_PAID");

		redirectAttributes.addFlashAttribute("successMessage","有料プランへの登録が完了しました。");
		return "redirect:/";
	}
	
//	有料会員情報の編集
	@GetMapping("/edit")
	public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, RedirectAttributes redirectAttributes, Model model) {
	    User user = userDetailsImpl.getUser();
	    String customerId = user.getStripeCustomerId();
	    
	    try {
//	    	デフォルトの支払い方法を取得する
			PaymentMethod paymentMethod = stripeService.getDefaultPaymentMethod(customerId);
//　　　　　　クレジットカード情報尾を取得しcardとして渡す。
			model.addAttribute("card", paymentMethod.getCard());
//			クレジットカードの名義人を取得し、cardHolderNameとして渡す。
			model.addAttribute("cardHolderName", paymentMethod.getBillingDetails().getName());
	    } catch(StripeException e) {
	    	redirectAttributes.addFlashAttribute("errorMessage", "お支払い方法の取得に失敗しました。再度お試しください。");
			return "redirect:/";
	    }
		return "subscription/edit";
	}
	
//	デフォルトの支払い方法を更新する
	@PostMapping("/update")
	public String update(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestParam String paymentMethodId, RedirectAttributes redirectAttributes) {
		User user = userDetailsImpl.getUser();
		String customerId = user.getStripeCustomerId();
		try {
//			デフォルトの支払い方法IDを取得する
			String defaultPaymentMethodId = stripeService.getDefaultPaymentMethodId(customerId);
//			フォームから送信された支払い方法をCustomerへ紐づける
			stripeService.attachPaymentMethodToCustomer(paymentMethodId, customerId);
//			フォームから送信された支払い方法をデフォルトの支払い方法に設定する
			stripeService.setDefaultPaymentMethod(paymentMethodId, customerId);
//			元々の支払い方法の紐付けを解除する
			stripeService.detachPaymentMethodFromCustomer(defaultPaymentMethodId);
		} catch (StripeException e) {
			redirectAttributes.addFlashAttribute("errorMessage", "お支払い方法の変更に失敗しました。再度お試しください。");
			return "redirect:/";
		}
		redirectAttributes.addFlashAttribute("successMessage","お支払い方法を変更しました。");
		return "redirect:/";
	}
	
//	有料会員解除ページへアクセスする
	@GetMapping("/cancel")
	public String cancel() {	
		return "subscription/cancel";
	}
	
//	サブスクリプションをキャンセルし、支払い方法と顧客の紐付けを解除する。
	@PostMapping("/delete")
	public String delete(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, RedirectAttributes redirectAttributes) {
		User user = userDetailsImpl.getUser();
		String customerId = user.getStripeCustomerId();
		try {
//			契約中のサブスク李ｐションを取得する
			List<Subscription> subscription = stripeService.getSubscriptions(customerId);
//			契約中のサブスクリプションをキャンセルする
			stripeService.cancelSubscriptions(subscription);
//			デフォルトの支払い方法IDを取得する
			String defaultPaymentMethodId = stripeService.getDefaultPaymentMethodId(customerId);
//			デフォルトの支払い方法の紐付けを解除する
			stripeService.detachPaymentMethodFromCustomer(defaultPaymentMethodId);
//			ロールを更新する
		} catch (StripeException e) {
			redirectAttributes.addFlashAttribute("errorMessage", "有料プランの解約に失敗しました。再度お試しください。");
			return "redirect:/";
		}
		
		userService.updateRole(user, "ROLE_GENERAL");
		userService.refreshAuthenticationByRole("ROLE_GENERAL");
		
		redirectAttributes.addFlashAttribute("successMessage","有料プランを解約しました。");
		return "redirect:/";
	}
	
}

