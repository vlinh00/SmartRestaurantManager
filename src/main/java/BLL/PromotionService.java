/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.PromotionRepository;
import DTO.Promotion;
import java.util.List;

/**
 *
 * @author lelin
 */
public class PromotionService {
    private final PromotionRepository promotionRepo = new PromotionRepository();

    public boolean addPromotion(Promotion promotion) {
        return promotionRepo.insert(promotion);
    }
    
    public boolean UpdatePromotion(Promotion promotion) {
        return promotionRepo.update(promotion);
    }

    public List<Promotion> getAllPromotions() {
        return promotionRepo.getAll();
    }

    public Promotion getPromotionById(int id) {
        return promotionRepo.getById(id);
    }

    public Promotion getBestApplicablePromotion(double orderTotal) {
        List<Promotion> all = promotionRepo.getAll();
        Promotion best = null;
        java.sql.Date today = new java.sql.Date(System.currentTimeMillis());

        for (Promotion p : all) {
            if (p.isActive() &&
                !today.before(p.getStartDate()) &&
                !today.after(p.getEndDate()) &&
                orderTotal >= p.getMinOrderAmount()) {

                if (best == null || p.getDiscountPercent() > best.getDiscountPercent()) {
                    best = p;
                }
            }
        }

        return best;
    }
}

