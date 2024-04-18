package com.love.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import com.love.backend.model.param.*;
import com.love.backend.model.vo.GoodsReviewStatVO;
import com.love.backend.model.vo.GoodsReviewVO;
import com.love.backend.model.vo.GoodsSimpleVO;
import com.love.backend.model.vo.GoodsVO;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageableUtil;
import com.love.common.util.RedisUtil;
import com.love.goods.bo.*;
import com.love.goods.client.BrandFeignClient;
import com.love.goods.client.CategoryFeignClient;
import com.love.goods.client.GoodsFeignClient;
import com.love.goods.client.LabelFeignClient;
import com.love.goods.dto.*;
import com.love.goods.enums.LabelStatus;
import com.love.goods.enums.LabelType;
import com.love.merchant.bo.MerUserAdminBusinessInfoQueryByBizName;
import com.love.merchant.client.MerUserBusinessInfoFeignClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.love.common.result.Result;

import javax.validation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GoodsManager {
    private final GoodsFeignClient goodsFeignClient;
    private final BrandFeignClient brandFeignClient;
    private final CategoryFeignClient categoryFeignClient;
    private final LabelFeignClient labelFeignClient;
    private final MerUserBusinessInfoFeignClient merUserBusinessInfoFeignClient;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    public Long savePhysical(PhysicalGoodsSaveParam physicalGoodsSaveParam) {
        PhysicalGoodsSaveBO physicalGoodsSaveBO = BeanUtil.copyProperties(physicalGoodsSaveParam, PhysicalGoodsSaveBO.class);
        return goodsFeignClient.savePhysical(physicalGoodsSaveBO);
    }

    public Result<Boolean> bulkSaveFromCsv(MultipartFile file) {
        StringBuffer errorMessage = new StringBuffer();

        if (Objects.isNull(file)) {
            throw BizException.build("File can't be empty");
        }

        if (!file.getContentType().equals("text/csv")) {
            throw BizException.build("File type is not CSV");
        }

        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));

            String[] HEADERS = { "Parentage", "Parent Product", "Type", "Category 1", "Category 2",
                    "Product Title", "Subtitle", "Merchant Name", "Brand", "Key Ingredients", "Serving Size",
                    "Servings Per Container", "Ingredient Name", "Body text", "Warnings", "How to Use", "SEO title", "SEO description",
                    "Option1 name", "Option1 value", "Option2 name", "Option2 value", "Option3 name", "Option3 value",
                    "Variant price", "Variant Inventory (On hand)", "Variant Inventory (Available)",
                    "Variant GTIN", "Variant MPN", "Variant SKU code", "Default SKU", "Image URLs"};

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(HEADERS).setSkipHeaderRecord(false).build();
            CSVParser csvParser = csvFormat.parse(fileReader);

            Iterator<CSVRecord> csvRecordsIterator = csvParser.getRecords().iterator();

            CSVRecord firstLine = csvRecordsIterator.next();
            if (!firstLine.toList().equals(Arrays.asList(HEADERS))) {
                return Result.fail("CSV file's header is not as expected, please double check with the template");
            }

            List<PhysicalGoodsSaveParam> physicalGoodsSaveParamsList = new ArrayList<PhysicalGoodsSaveParam>();

            readAndVerifyCsvContent(csvRecordsIterator, physicalGoodsSaveParamsList, errorMessage);

            String errors = errorMessage.toString();
            if (errors.isEmpty()) {
                for (PhysicalGoodsSaveParam physicalGoodsSaveParam: physicalGoodsSaveParamsList) {
                    PhysicalGoodsSaveBO physicalGoodsSaveBO = BeanUtil.copyProperties(physicalGoodsSaveParam, PhysicalGoodsSaveBO.class);
                    goodsFeignClient.savePhysical(physicalGoodsSaveBO);
                }
            } else {
                return Result.fail(errors);
            }

        } catch (IOException e) {
            throw new BizException("Fail to parse CSV file: " + e.getMessage());
        }

        return Result.success(true);
    }

    public GoodsVO detail(IdParam idParam) {
        GoodsDTO goodsDTO = goodsFeignClient.detail(GoodsDetailQueryBO.builder().id(idParam.getId()).build());
        return BeanUtil.copyProperties(goodsDTO, GoodsVO.class);
    }

    public Boolean deleteById(IdParam idParam) {
        return goodsFeignClient.deleteById(idParam);
    }

    public Pageable<GoodsSimpleVO> page(GoodsQueryPageParam goodsQueryPageParam) {
        GoodsQueryPageBO goodsQueryPageBO = BeanUtil.copyProperties(goodsQueryPageParam, GoodsQueryPageBO.class);
        Pageable<GoodsSimpleDTO> pageable = goodsFeignClient.page(goodsQueryPageBO);
        return PageableUtil.toPage(pageable, GoodsSimpleVO.class);
    }

    public Pageable<GoodsReviewVO> reviewPage(GoodsReviewPageParam goodsReviewPageParam) {
        GoodsReviewPageQueryBO goodsReviewPageBO = BeanUtil.copyProperties(goodsReviewPageParam, GoodsReviewPageQueryBO.class);
        Pageable<GoodsReviewDTO> pageable = goodsFeignClient.reviewPage(goodsReviewPageBO);
        return PageableUtil.toPage(pageable, GoodsReviewVO.class);
    }

    public Boolean reviewApprove(GoodsReviewApproveParam approveParam) {
        GoodsReviewApproveBO goodsReviewApproveBO = BeanUtil.copyProperties(approveParam, GoodsReviewApproveBO.class);
        return goodsFeignClient.reviewApprove(goodsReviewApproveBO);
    }

    public Boolean reviewReject(GoodsReviewRejectParam rejectParam) {
        GoodsReviewRejectBO goodsReviewRejectBO = BeanUtil.copyProperties(rejectParam, GoodsReviewRejectBO.class);
        return goodsFeignClient.reviewReject(goodsReviewRejectBO);
    }

    public Boolean putOff(IdParam idParam) {
        return goodsFeignClient.putOff(idParam);
    }

    public Boolean putOn(IdParam idParam) {
        return goodsFeignClient.putOn(idParam);
    }

    public Long duplicate(GoodsDuplicateParam duplicateParam) {
        GoodsDuplicateBO goodsDuplicateBO = BeanUtil.copyProperties(duplicateParam, GoodsDuplicateBO.class);
        return goodsFeignClient.duplicate(goodsDuplicateBO);
    }

    public List<GoodsReviewStatVO> reviewStat() {
        List<GoodsReviewStatDTO> goodsReviewStatusDTOList = goodsFeignClient.reviewStat();
        return BeanUtil.copyToList(goodsReviewStatusDTOList, GoodsReviewStatVO.class);
    }

    private Map<String, Long> getBrandMap(Long merchantId) {
        List<BrandDTO> curMerchantBrandList = brandFeignClient.queryByMerchantId(BrandQueryListBO.builder().merchantId(merchantId).build());

        Map<String, Long> res = new HashMap<>();
        for (BrandDTO brandDTO: curMerchantBrandList) {
            res.put(brandDTO.getName(), brandDTO.getId());
        }

        return res;
    }

    private Map<String, Map<String, Long>> getCategoryMap(int level) {
        Pageable<CategoryDTO> categoryList = categoryFeignClient.page(CategoryQueryPageBO.builder().pageNum(1).pageSize(9999).type(0).level(level).build());

        Map<String, Map<String, Long>> res = new HashMap<>();
        for (CategoryDTO categoryDto: categoryList.getRecords()) {
            Map<String, Long> idPid = new HashMap<>();
            idPid.put("id", categoryDto.getId());
            idPid.put("pid", categoryDto.getPid());
            res.put(categoryDto.getName(), idPid);
        }

        return res;
    }

    private Map<String, Long> getLabelMap() {
        Map<String, Long> res = new HashMap<>();
        Pageable<LabelDTO> labels = labelFeignClient.page(LabelQueryPageBO.builder().
                pageNum(1).
                pageSize(9999).
                type(LabelType.INGREDIENT.getType()).
                status(LabelStatus.ENABLE.getStatus()).
                build());
        for (LabelDTO label: labels.getRecords()) {
            res.put(label.getName(), label.getId());
        }

        return res;
    }

    private void readAndVerifyCsvContent(Iterator<CSVRecord> csvRecordsIterator,
                                         List<PhysicalGoodsSaveParam> physicalGoodsSaveParamsList,
                                         StringBuffer errorMessage) {
        // Maps to object id based on name, the size of these map won't be large, so we read them in memory
        Map<String, Map<String, Long>> firstLevelCategoryMap = getCategoryMap(1);
        Map<String, Map<String, Long>> secondLevelCategoryMap = getCategoryMap(2);
        Map<String, Long> labelMap = getLabelMap();
        Long merchantId = null;
        String merchantName = null;
        Map<String, Long> brandMap = null;

        PhysicalGoodsSaveParam curPhysicalGoodsSaveParam = null;
        /*
            [
                {
                    "name": ("Flavour"),
                    "value": ("Apple", "Orange")
                },
                {
                    "name": ("Size"),
                    "value": ("S", "L")
                },
                {
                    "name": (""),
                    "value": ("")
                }
            ]
        */
        List<Map<String, Set<String>>> curVariants = new ArrayList<>();
        Set<String> curSkuAttrValues = new HashSet<>();
        List<GoodsSkuSaveParam> curSkusList = new ArrayList<>();
        long curParentLine = 0;
        boolean allSkusValid = true;

        while (csvRecordsIterator.hasNext()) {
            CSVRecord curRecord = csvRecordsIterator.next();

            if (curRecord.get("Parentage").equals("Parent")) {
                addValidProductToSaveList(curPhysicalGoodsSaveParam, allSkusValid, curVariants, curSkusList, curParentLine, physicalGoodsSaveParamsList, errorMessage);

                // Check this first product's merchant name is valid
                if (curRecord.getRecordNumber() == 2) {
                    merchantName = curRecord.get("Merchant Name");
                    if (merchantName.trim().isEmpty()) {
                        throw BizException.build("First line's merchant name can't be empty");
                    }
                    merchantId = merUserBusinessInfoFeignClient.queryByBizName(MerUserAdminBusinessInfoQueryByBizName.builder().bizName(merchantName).build()).getAdminId();
                    if (Objects.isNull(merchantId)) {
                        throw BizException.build("First line's merchant name is not valid");
                    }

                    brandMap = getBrandMap(merchantId);
                }

                curPhysicalGoodsSaveParam = PhysicalGoodsSaveParam.builder().merchantId(merchantId).build();
                curVariants = new ArrayList<>();
                curSkuAttrValues = new HashSet<String>();
                curSkusList = new ArrayList<GoodsSkuSaveParam>();
                curParentLine = curRecord.getRecordNumber();

                fillParentLineInPhysicalGoodsSaveParam(curRecord, merchantName, brandMap, curPhysicalGoodsSaveParam,
                        curVariants, firstLevelCategoryMap, secondLevelCategoryMap, labelMap, errorMessage);
                GoodsSkuSaveParam goodsSkuSaveParam = new GoodsSkuSaveParam();
                allSkusValid = fillCurrentLineInGoodsSkuSaveParam(curRecord, goodsSkuSaveParam, curVariants, curSkuAttrValues, errorMessage);
                curSkusList.add(goodsSkuSaveParam);
            } else if (curRecord.get("Parentage").equals("Child")) {
                if (Objects.isNull(curPhysicalGoodsSaveParam)) {
                    errorMessage.append("Line ").append(curRecord.getRecordNumber()).append(" has a error: Child line does not have a Parent line\n");
                }
                if (curParentLine == curRecord.getRecordNumber() - 1) {
                    curSkuAttrValues = new HashSet<>();
                    curSkusList = new ArrayList<>();
                    for (Map<String, Set<String>> variantMap: curVariants) {
                        variantMap.put("value", new HashSet<>());
                    }
                    allSkusValid = true;
                }
                GoodsSkuSaveParam goodsSkuSaveParam = new GoodsSkuSaveParam();
                boolean curSkuValid = fillCurrentLineInGoodsSkuSaveParam(curRecord, goodsSkuSaveParam, curVariants, curSkuAttrValues, errorMessage);
                allSkusValid = allSkusValid && curSkuValid;
                curSkusList.add(goodsSkuSaveParam);
            } else {
                errorMessage.append("Line ").append(curRecord.getRecordNumber()).append(" is neither Parent or Child, please check the Parentage type\n");
            }
        }

        addValidProductToSaveList(curPhysicalGoodsSaveParam, allSkusValid, curVariants, curSkusList, curParentLine, physicalGoodsSaveParamsList, errorMessage);
    }

    private boolean fillParentLineInPhysicalGoodsSaveParam(CSVRecord curRecord,
                                                           String merchantName,
                                                           Map<String, Long> brandMap,
                                                           PhysicalGoodsSaveParam curPhysicalGoodsSaveParam,
                                                           List<Map<String, Set<String>>> curVariants,
                                                           Map<String, Map<String, Long>> firstLevelCategoryMap,
                                                           Map<String, Map<String, Long>> secondLevelCategoryMap,
                                                           Map<String, Long> labelMap,
                                                           StringBuffer errorMessage) {
        long curLineNum = curRecord.getRecordNumber();
        boolean isValid = true;

        // We only support Physical Product now
        curPhysicalGoodsSaveParam.setType(1);

        if (!curRecord.get("Merchant Name").trim().equals(merchantName)) {
            errorMessage.append("Line ").append(curLineNum).append(" has error: merchant name is not same as first line. We only support one merchant now.\n");
            isValid = false;
        }

        buildBrand(curPhysicalGoodsSaveParam, curRecord, brandMap, errorMessage);
        buildCategory(curPhysicalGoodsSaveParam, curRecord, firstLevelCategoryMap, secondLevelCategoryMap, errorMessage);
        buildKeyIngredients(curPhysicalGoodsSaveParam, curRecord, labelMap, errorMessage);

        curPhysicalGoodsSaveParam.setTitle(curRecord.get("Product Title").trim());
        curPhysicalGoodsSaveParam.setSubTitle(curRecord.get("Subtitle").trim());
        curPhysicalGoodsSaveParam.setServingSize(curRecord.get("Serving Size").trim());
        curPhysicalGoodsSaveParam.setServingSize(curRecord.get("Servings Per Container").trim());
        buildIngredients(curPhysicalGoodsSaveParam, curRecord, errorMessage);

        String bodyText = curRecord.get("Body text").trim();
        String howToUse = curRecord.get("How to Use").trim();
        if (bodyText.isEmpty() || howToUse.isEmpty()) {
            errorMessage.append("Line ").append(curLineNum).append(" has error: Body text and How to Use can't be empty.\n");
        } else {
            curPhysicalGoodsSaveParam.setDescText(String.format("<p>%s</p>", bodyText));
            curPhysicalGoodsSaveParam.setIntro(String.format("<p>%s</p>", howToUse));
        }

        curPhysicalGoodsSaveParam.setDescWarnings(String.format("<p>%s</p>", curRecord.get("Warnings").trim()));
        curPhysicalGoodsSaveParam.setSearchPageTitle(curRecord.get("SEO title"));
        curPhysicalGoodsSaveParam.setSearchMetaDescription(curRecord.get("SEO description"));

        curPhysicalGoodsSaveParam.setMainImages("no image");
        curPhysicalGoodsSaveParam.setWhiteBgImg("no image");

        Set<String> nonEmptyOptionNames = new HashSet<String>();
        for (int i = 1; i <= 3; i++) {
            String optionNameColumnName = String.format("Option%s name", i);
            String optionName = curRecord.get(optionNameColumnName).trim();

            if (!optionName.isEmpty()) {
                nonEmptyOptionNames.add(optionName);
            }

            Map<String, Set<String>> optionMap = new HashMap<>();
            optionMap.put("name", new HashSet<String>(Collections.singletonList(optionName)));
            optionMap.put("value", new HashSet<String>());
            curVariants.add(optionMap);
        }
        if (nonEmptyOptionNames.isEmpty()) {
            errorMessage.append("Line ").append(curLineNum).append(" has error: must have at least one option name.\n");
            isValid = false;
        }

        curPhysicalGoodsSaveParam.setGoodsDoc(new PhysicalGoodsSaveParam.GoodsDoc());

        curPhysicalGoodsSaveParam.setPublish(false);

        return isValid;
    }

    private boolean fillCurrentLineInGoodsSkuSaveParam(CSVRecord curRecord,
                                                       GoodsSkuSaveParam goodsSkuSaveParam,
                                                       List<Map<String, Set<String>>> curVariants,
                                                       Set<String> curSkuAttrValues,
                                                       StringBuffer errorMessage) {
        boolean isValid = true;

        goodsSkuSaveParam.setCover("no image");

        List<String> optionValues = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            String optionNameTitleName = String.format("Option%s name", i);
            String optionName = curRecord.get(optionNameTitleName).trim();
            String optionValueTitleName = String.format("Option%s value", i);
            String optionValue = curRecord.get(optionValueTitleName).trim();

            if (!(curVariants.get(i - 1).get("name").contains(optionName))) {
                errorMessage.append("Line ").append(curRecord.getRecordNumber()).
                       append(String.format(" has error: Option%s name does not match with parent\n", i));
                isValid = false;
            } else if (!optionName.isEmpty() && optionValue.isEmpty()) {
                errorMessage.append("Line ").append(curRecord.getRecordNumber()).
                        append(String.format(" has error: Option%s value is empty\n", i));
                isValid = false;
            } else if (!optionName.isEmpty()) {
                curVariants.get(i - 1).get("value").add(optionValue);
                optionValues.add(optionValue);
            }
        }

        String variantValue = String.join("/", optionValues);
        if (curSkuAttrValues.contains(variantValue)) {
            errorMessage.append("Line ").append(curRecord.getRecordNumber()).
                   append(String.format(" has error: %s has already been defined in previous lines\n", variantValue));
            isValid = false;
        } else {
            curSkuAttrValues.add(variantValue);
            goodsSkuSaveParam.setAttrValues(variantValue);
        }

        try {
            String variantPrice = curRecord.get("Variant price").trim();
            if (!variantPrice.equals("-")) {
                goodsSkuSaveParam.setPrice(new BigDecimal(variantPrice));
            }
        } catch (NumberFormatException e) {
            errorMessage.append("Line ").append(curRecord.getRecordNumber()).
                    append(" has error: variant price is not a valid number\n");
            isValid = false;
        }

        try {
            String onStock = curRecord.get("Variant Inventory (On hand)").trim();
            if (!onStock.equals("-")) {
                goodsSkuSaveParam.setOnHandStock(Integer.parseInt(onStock));
            }
        } catch (NumberFormatException e) {
            errorMessage.append("Line ").append(curRecord.getRecordNumber()).
                    append(" has error: Variant Inventory (On hand) is not a valid number\n");
            isValid = false;
        }

        try {
            String available = curRecord.get("Variant Inventory (Available)");
            if (!available.equals("-")) {
                goodsSkuSaveParam.setAvailableStock(Integer.parseInt(available));
            }
        } catch (NumberFormatException e) {
            errorMessage.append("Line ").append(curRecord.getRecordNumber()).
                    append(" has error: Variant Inventory (Available) is not a valid number\n");
            isValid = false;
        }

        goodsSkuSaveParam.setCode(curRecord.get("Variant SKU code"));
        // Shipping weight and weight unit are set as 0 and 'lb' as default value for CSV upload
        goodsSkuSaveParam.setShippingWeight(BigDecimal.ZERO);
        goodsSkuSaveParam.setShippingWeightUnit("lb");
        goodsSkuSaveParam.setGtin(curRecord.get("Variant GTIN"));
        goodsSkuSaveParam.setMpn(curRecord.get("Variant MPN"));
        if (curRecord.get("Default SKU").equals("TRUE")) {
            goodsSkuSaveParam.setDefaultSku(1);
        }
        goodsSkuSaveParam.setStatus(1);

        if (!curRecord.get("Variant price").trim().equals("-") && !curRecord.get("Variant Inventory (On hand)").trim().equals("-")
            && !curRecord.get("Variant Inventory (Available)").trim().equals("-")) {
            Set<ConstraintViolation<GoodsSkuSaveParam>> violations = validator.validate(goodsSkuSaveParam);
            if (!violations.isEmpty()) {
                isValid = false;

                errorMessage.append("Line ").append(curRecord.getRecordNumber())
                        .append(" is not valid SKU due to the following reason:\n");

                for (ConstraintViolation<GoodsSkuSaveParam> violation : violations) {
                    errorMessage.append(violation.getMessage()).append("; \n");
                }
            }
        }


        return isValid;
    }

    private void buildBrand(PhysicalGoodsSaveParam curPhysicalGoodsSaveParam,
                            CSVRecord curRecord,
                            Map<String, Long> brandMap,
                            StringBuffer errorMessage) {
        String brandName = curRecord.get("Brand").trim();
        if (brandMap.containsKey(brandName)) {
            curPhysicalGoodsSaveParam.setBrandId(brandMap.get(brandName));
        } else {
            errorMessage.append("Line ").append(curRecord.getRecordNumber())
                    .append(String.format(" has error: brand %s does not belong to this merchant\n", brandName));
        }
    }

    private void buildCategory(PhysicalGoodsSaveParam curPhysicalGoodsSaveParam,
                               CSVRecord curRecord,
                               Map<String, Map<String, Long>> firstLevelCategoryMap,
                               Map<String, Map<String, Long>> secondLevelCategoryMap,
                               StringBuffer errorMessage) {
        Long curLineNum = curRecord.getRecordNumber();

        String firstCategoryName = curRecord.get("Category 1").trim();
        String secondCategoryName = curRecord.get("Category 2").trim();
        if (firstCategoryName.isEmpty()) {
            errorMessage.append("Line ").append(curLineNum).append(" has error: Category 1 must be defined\n");
        } else if (!secondCategoryName.isEmpty()){
            if (firstLevelCategoryMap.containsKey(firstCategoryName)
                    && secondLevelCategoryMap.containsKey(secondCategoryName)
                    && Objects.equals(secondLevelCategoryMap.get(secondCategoryName).get("pid"), firstLevelCategoryMap.get(firstCategoryName).get("id"))) {
                curPhysicalGoodsSaveParam.setFirstCateId(firstLevelCategoryMap.get(firstCategoryName).get("id"));
                curPhysicalGoodsSaveParam.setSecondCateId(secondLevelCategoryMap.get(secondCategoryName).get("id"));
            } else {
                errorMessage.append("Line ").append(curLineNum)
                        .append(String.format(" has error: first category %s and second category %s are not a valid pair in our system\n", firstCategoryName, secondCategoryName));
            }
        } else {
            if (firstLevelCategoryMap.containsKey(firstCategoryName)) {
                curPhysicalGoodsSaveParam.setFirstCateId(firstLevelCategoryMap.get(firstCategoryName).get("id"));
            } else {
                errorMessage.append("Line ").append(curLineNum)
                        .append(String.format(" has error: first category %s is not in our system\n", firstCategoryName));
            }
        }

    }

    private void buildKeyIngredients(PhysicalGoodsSaveParam curPhysicalGoodsSaveParam,
                                     CSVRecord curRecord,
                                     Map<String, Long> labelMap,
                                     StringBuffer errorMessage) {
        List<LabelSaveParam> res = new ArrayList<>();

        for (String ingredient: curRecord.get("Key Ingredients").split("/")) {
            LabelSaveParam labelSaveParam = new LabelSaveParam();
            labelSaveParam.setName(ingredient);
            if (labelMap.containsKey(ingredient)) {
                labelSaveParam.setId(labelMap.get(ingredient));
            }
            res.add(labelSaveParam);
        }

        curPhysicalGoodsSaveParam.setKeyIngredients(res);
    }

    private void buildIngredients(PhysicalGoodsSaveParam curPhysicalGoodsSaveParam,
                                     CSVRecord curRecord,
                                     StringBuffer errorMessage) {
        List<IngredientParam> res = new ArrayList<>();
        for (String ingredientStr: curRecord.get("Ingredient Name").split("/")) {
            if (!ingredientStr.trim().isEmpty()) {
                IngredientParam param = new IngredientParam();

                String[] parts = ingredientStr.split("-",2);
                param.setName(parts[0]);
                if (parts.length > 1) {
                    param.setAmount(parts[1]);
                }

                res.add(param);
            }
        }

        curPhysicalGoodsSaveParam.setIngredients(res);
    }



    private void addValidProductToSaveList(PhysicalGoodsSaveParam curPhysicalGoodsSaveParam,
                                           boolean allSkusValid,
                                           List<Map<String, Set<String>>> curVariants,
                                           List<GoodsSkuSaveParam> curSkusList,
                                           Long curParentLine,
                                           List<PhysicalGoodsSaveParam> physicalGoodsSaveParamsList,
                                           StringBuffer errorMessage) {
        if (!Objects.isNull(curPhysicalGoodsSaveParam)) {
            curPhysicalGoodsSaveParam.setVariants(buildVariants(curVariants));
            if (allSkusValid) {
                curPhysicalGoodsSaveParam.setSkus(curSkusList);
            } else {
                errorMessage.append("Product starts from line: ").append(curParentLine).append(" has error: not all child lines are valid\n");
            }
            if (isValidateProduct(curPhysicalGoodsSaveParam, errorMessage, curParentLine)) {
                physicalGoodsSaveParamsList.add(curPhysicalGoodsSaveParam);
            }
        }

    }

    private boolean isValidateProduct(PhysicalGoodsSaveParam curPhysicalGoodsSaveParam, StringBuffer errorMessage, long curParentLine) {
        if (Objects.isNull(curPhysicalGoodsSaveParam)) {
            return false;
        }

        Set<ConstraintViolation<PhysicalGoodsSaveParam>> violations = validator.validate(curPhysicalGoodsSaveParam);
        if (violations.isEmpty()) {
            return true;
        }

        errorMessage.append("Product starts from line: ").append(curParentLine).append(" is not valid due to the following reason:\n");
        for (ConstraintViolation<PhysicalGoodsSaveParam> violation : violations) {
            errorMessage.append(violation.getMessage()).append("; \n");
        }

        return false;
    }

    private List<AttrNameSaveParam> buildVariants(List<Map<String, Set<String>>> curVariants) {
        List<AttrNameSaveParam> result = new ArrayList<AttrNameSaveParam>();
        for (Map<String, Set<String>> variantMap: curVariants) {
            String optionName = new ArrayList<>(variantMap.get("name")).get(0);
            if (!optionName.trim().isEmpty()) {
                AttrNameSaveParam attrNameSaveParam = new AttrNameSaveParam();
                attrNameSaveParam.setName(new ArrayList<>(variantMap.get("name")).get(0));

                List<AttrValueSaveParam> values = new ArrayList<AttrValueSaveParam>();
                for (String optionValue: variantMap.get("value")) {
                    AttrValueSaveParam valueSaveParam = new AttrValueSaveParam();
                    valueSaveParam.setValue(optionValue);
                    values.add(valueSaveParam);
                }
                attrNameSaveParam.setValues(values);

                result.add(attrNameSaveParam);
            }
        }

        return result;
    }

}
