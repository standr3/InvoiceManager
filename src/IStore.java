public interface IStore {

    Double getTotalTaxFree();

    Double getTotal();

    Double getTotalLowTax();

    Double getCountryTotalTaxFree(String country);

    Double getCountryTotal(String country);

    Double getCountryTotalLowTax(String country);

    Double getLoweredPercentage();
}
