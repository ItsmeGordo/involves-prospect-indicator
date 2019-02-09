package br.com.involves.prospectIndicator.controller.shop;

import br.com.involves.prospectIndicator.dto.ShopDistanceDTO;
import br.com.involves.prospectIndicator.facade.shop.ShopFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    @Autowired
    private ShopFacade shopFacade;

    @GetMapping("/getShopsInRadius")
    public List<ShopDistanceDTO> getShopsInRadius(ShopVMI vmi) {
        return shopFacade.getShopDistanceDTOS(vmi);
    }

    @GetMapping(value = "/downloadShopsInRadius", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity downloadShopsInRadius(ShopVMI vmi) {
        return ResponseEntity.ok(shopFacade.getBytesFromShopDistanceCsv(vmi));
    }

    @GetMapping(value = "downloadBestRoute", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity downloadBestRoute(ShopVMI vmi) {
        return ResponseEntity.ok(shopFacade.getByteFromBestRoute(vmi));
    }

}
