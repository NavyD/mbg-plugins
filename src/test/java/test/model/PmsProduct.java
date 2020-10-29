package test.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import xyz.navyd.mbg.entity.BaseEntity;

/**
 * Database Table Remarks:
 *   商品表. 商品信息主要包括四部分：商品的基本信息、商品的促销信息、商品的属性信息、商品的关联，商品表是整个商品的基本信息部分。
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table pms_product
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class PmsProduct extends BaseEntity implements Serializable {
    /**
     * 品牌id
     *
     * @mbg.generated
     */
    private Long brandId;

    /**
     * 品牌分类id
     *
     * @mbg.generated
     */
    private Long productCategoryId;

    /**
     * 运费模版id
     *
     * @mbg.generated
     */
    private Long feightTemplateId;

    /**
     * 品牌属性分类id
     *
     * @mbg.generated
     */
    private Long productAttributeCategoryId;

    /**
     * 商品名称
     *
     * @mbg.generated
     */
    private String productName;

    /**
     * 图片
     *
     * @mbg.generated
     */
    private String pic;

    /**
     * 货号
     *
     * @mbg.generated
     */
    private String productSn;

    /**
     * 删除状态：0->未删除；1->已删除
     *
     * @mbg.generated
     */
    private Boolean deleted;

    /**
     * 上架状态：0->下架；1->上架
     *
     * @mbg.generated
     */
    private Boolean published;

    /**
     * 新品状态:0->不是新品；1->新品
     *
     * @mbg.generated
     */
    private Boolean fresh;

    /**
     * 推荐状态；0->不推荐；1->推荐
     *
     * @mbg.generated
     */
    private Boolean recommanded;

    /**
     * 审核状态：0->未审核；1->审核通过
     *
     * @mbg.generated
     */
    private Boolean verrified;

    /**
     * 排序
     *
     * @mbg.generated
     */
    private Integer sort;

    /**
     * 销量
     *
     * @mbg.generated
     */
    private Integer sale;

    /**
     * 价格
     *
     * @mbg.generated
     */
    private BigDecimal price;

    /**
     * 促销价格
     *
     * @mbg.generated
     */
    private BigDecimal promotionPrice;

    /**
     * 赠送的成长值
     *
     * @mbg.generated
     */
    private Integer giftGrowth;

    /**
     * 赠送的积分
     *
     * @mbg.generated
     */
    private Integer giftPoint;

    /**
     * 限制使用的积分数
     *
     * @mbg.generated
     */
    private Integer usePointLimit;

    /**
     * 副标题
     *
     * @mbg.generated
     */
    private String subTitle;

    /**
     * 市场价
     *
     * @mbg.generated
     */
    private BigDecimal originalPrice;

    /**
     * 库存
     *
     * @mbg.generated
     */
    private Integer stock;

    /**
     * 库存预警值
     *
     * @mbg.generated
     */
    private Integer lowStock;

    /**
     * 单位
     *
     * @mbg.generated
     */
    private String unit;

    /**
     * 商品重量，默认为克
     *
     * @mbg.generated
     */
    private BigDecimal productWeight;

    /**
     * 是否为预告商品：0->不是；1->是
     *
     * @mbg.generated
     */
    private Boolean previewed;

    /**
     * 以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮
     *
     * @mbg.generated
     */
    private String serviceIds;

    /**
     * 关键字
     *
     * @mbg.generated
     */
    private String keywords;

    /**
     * 备注
     *
     * @mbg.generated
     */
    private String note;

    /**
     * 画册图片，连产品图片限制为5张，以逗号分割
     *
     * @mbg.generated
     */
    private String albumPics;

    /**
     * 详情标题
     *
     * @mbg.generated
     */
    private String detailTitle;

    /**
     * 促销开始时间
     *
     * @mbg.generated
     */
    private LocalDateTime promotionStartTime;

    /**
     * 促销结束时间
     *
     * @mbg.generated
     */
    private LocalDateTime promotionEndTime;

    /**
     * 活动限购数量
     *
     * @mbg.generated
     */
    private Integer promotionPerLimit;

    /**
     * 促销类型：0->没有促销使用原价;1->使用促销价；2->使用会员价；3->使用阶梯价格；4->使用满减价格；5->限时购
     *
     * @mbg.generated
     */
    private Byte promotionStatus;

    /**
     * 产品分类名称
     *
     * @mbg.generated
     */
    private String productCategoryName;

    /**
     * 品牌名称
     *
     * @mbg.generated
     */
    private String brandName;

    /**
     * 商品描述
     *
     * @mbg.generated
     */
    private String productDescription;

    /**
     * 详情描述
     *
     * @mbg.generated
     */
    private String detailDesc;

    /**
     * 产品详情网页内容
     *
     * @mbg.generated
     */
    private String detailHtml;

    /**
     * 移动端网页详情
     *
     * @mbg.generated
     */
    private String detailMobileHtml;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pms_product
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
}