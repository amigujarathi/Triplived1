package com.connectme.domain.triplived;

public class Device {
    String osVersion;
    int sdkVersion;
    String device;
    String model;
    String product;
    String manufacturer;
    String brand;
    String bootloader;
    
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public int getSdkVersion() {
		return sdkVersion;
	}
	public void setSdkVersion(int sdkVersion) {
		this.sdkVersion = sdkVersion;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getBootloader() {
		return bootloader;
	}
	public void setBootloader(String bootloader) {
		this.bootloader = bootloader;
	}
	@Override
	public String toString() {
		return "Device [osVersion=" + osVersion + ", sdkVersion=" + sdkVersion
				+ ", device=" + device + ", model=" + model + ", product="
				+ product + ", manufacturer=" + manufacturer + ", brand="
				+ brand + ", bootloader=" + bootloader + "]";
	}
}