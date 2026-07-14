package com.sena.ecommerce.apicontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.ecommerce.dto.ProductoDTO;
import com.sena.ecommerce.dto.ProductoMapper;
import com.sena.ecommerce.dto.ProductoRequestDTO;
import com.sena.ecommerce.model.Producto;
import com.sena.ecommerce.model.Usuario;
import com.sena.ecommerce.service.IProductoService;
import com.sena.ecommerce.service.IUsuarioService;
import com.sena.ecommerce.service.UploadFileService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/apiproductos")
public class ApiProductoController {

	private final IProductoService productoservice;

	private final UploadFileService upload;

	private final IUsuarioService usuarioservice;

	ApiProductoController(IProductoService productoservice, IUsuarioService usuarioservice, UploadFileService upload) {
		this.productoservice = productoservice;
		this.usuarioservice = usuarioservice;
		this.upload = upload;
	}

	@GetMapping("/api")
	public List<ProductoDTO> getAllProducts() {
		return productoservice.findAll().stream().map(ProductoMapper::toDTO).toList();
	}

	@GetMapping("/api/{id}")
	public ResponseEntity<ProductoDTO> getProductById(@PathVariable Integer id) {
		return productoservice.get(id).map(ProductoMapper::toDTO).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	// Antes: usuarioservice.findById(4).get() — hardcodeado y sin control de
	// sesión. Ahora toma el usuario autenticado real desde la sesión, y
	// responde 401 si no hay sesión activa.
	@PostMapping("/api")
	public ResponseEntity<?> createProduct(@Valid @RequestBody ProductoRequestDTO request, HttpSession session) {
		Usuario usuario = usuarioActual(session);
		if (usuario == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Debe iniciar sesión para crear productos");
		}

		Producto producto = ProductoMapper.toEntity(request);
		producto.setUsuario(usuario);

		Producto guardado = productoservice.save(producto);
		return ResponseEntity.status(HttpStatus.CREATED).body(ProductoMapper.toDTO(guardado));
	}

	@PutMapping("/api/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Integer id, @Valid @RequestBody ProductoRequestDTO request) {
		Optional<Producto> existente = productoservice.get(id);
		if (existente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Producto producto = existente.get();
		ProductoMapper.copyToEntity(request, producto);
		productoservice.update(producto);

		return ResponseEntity.ok(ProductoMapper.toDTO(producto));
	}

	@DeleteMapping("/api/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
		Optional<Producto> producto = productoservice.get(id);
		if (producto.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Producto p = producto.get();
		// Antes: p.getImagen().equals("default.jpg") reventaba con NPE si
		// getImagen() era null.
		if (p.getImagen() != null && !p.getImagen().equals("default.jpg")) {
			upload.deleteImage(p.getImagen());
		}

		productoservice.delete(id);
		return ResponseEntity.noContent().build();
	}

	private Usuario usuarioActual(HttpSession session) {
		Object idUsuario = session.getAttribute("idUsuario");
		if (idUsuario == null) {
			return null;
		}
		return usuarioservice.findById(Integer.parseInt(idUsuario.toString())).orElse(null);
	}

}
