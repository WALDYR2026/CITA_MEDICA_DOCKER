package com.citamedica.cita_medica.Model;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
@Embeddable
public class DetalleCitaId implements Serializable {
 @Column(name="CODIGO_CIT",length=8) private String codigoCit;
 @Column(name="CODIGO_SERV",length=8) private String codigoServ;
 public DetalleCitaId(){}
 public DetalleCitaId(String c,String s){codigoCit=c;codigoServ=s;}
 public String getCodigoCit(){return codigoCit;} public void setCodigoCit(String x){codigoCit=x;}
 public String getCodigoServ(){return codigoServ;} public void setCodigoServ(String x){codigoServ=x;}
 @Override public boolean equals(Object o){if(this==o)return true;if(!(o instanceof DetalleCitaId d))return false;return Objects.equals(codigoCit,d.codigoCit)&&Objects.equals(codigoServ,d.codigoServ);}
 @Override public int hashCode(){return Objects.hash(codigoCit,codigoServ);}
}
