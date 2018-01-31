## product-info task
---

The `product-info` task displays product information such as product name and version of the Liberty runtime. The output includes information for product extensions if a *product_extension*.properties file is provided in the product extension installation's versions directory.

#### Parameters

Only the [common parameters](common-parameters.md#common-parameters) are supported by this task.

#### Examples

Display the product information.
 
```ant
<wlp:product-info installDir="${wlp_install_dir}" />
```