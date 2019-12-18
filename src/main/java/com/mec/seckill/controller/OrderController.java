package com.mec.seckill.controller;

import com.mec.seckill.service.GoodsService;
import com.mec.seckill.service.MiaoshaUserService;
import com.mec.seckill.service.OrderService;
import com.mec.seckill.vo.GoodsVo;
import com.mec.seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mec.seckill.domain.MiaoshaUser;
import com.mec.seckill.domain.OrderInfo;
import com.mec.seckill.redis.RedisService;
import com.mec.seckill.result.CodeMsg;
import com.mec.seckill.result.Result;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
    MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
    OrderService orderService;
	
	@Autowired
    GoodsService goodsService;
	
    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, MiaoshaUser user,
                                      @RequestParam("orderId") long orderId) {
    	if(user == null) {
    		return Result.error(CodeMsg.SESSION_ERROR);
    	}
    	OrderInfo order = orderService.getOrderById(orderId);
    	if(order == null) {
    		return Result.error(CodeMsg.ORDER_NOT_EXIST);
    	}
    	long goodsId = order.getGoodsId();
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	OrderDetailVo vo = new OrderDetailVo();
    	vo.setOrder(order);
    	vo.setGoods(goods);
    	return Result.success(vo);
    }
    
}
