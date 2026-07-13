// datatables
$(document).ready(function () {
    $("#myTable").DataTable({
        language: {
            url: "//cdn.datatables.net/plug-ins/2.3.8/i18n/es-ES.json",
        },
        pageLength: 5,
        lengthMenu: [
            [5, 10, 25, 50, 100],
            [5, 10, 25, 50, 100],
        ],
    });
});
(function () {
    const sidebar = document.getElementById('sidebar');

    // ---------- CHART.JS: gráficas profesionales ----------
    // Gráfico de ventas mensuales (línea)
    const ctxSales = document.getElementById('salesChart')?.getContext('2d');
    if (ctxSales) {
        new Chart(ctxSales, {
            type: 'line',
            data: {
                labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
                datasets: [{
                    label: 'Ingresos (USD)',
                    data: [3200, 4100, 4800, 5400, 6800, 7900, 8500, 9200, 10500, 11200, 12800, 14300],
                    borderColor: '#2c7da0',
                    backgroundColor: 'rgba(44, 125, 160, 0.05)',
                    borderWidth: 2.5,
                    pointBackgroundColor: '#2c7da0',
                    pointBorderColor: '#fff',
                    pointRadius: 4,
                    pointHoverRadius: 7,
                    tension: 0.3,
                    fill: true,
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: { position: 'top', labels: { boxWidth: 12, font: { size: 12 } } },
                    tooltip: { callbacks: { label: (ctx) => `$${ctx.raw.toLocaleString()}` } }
                },
                scales: { y: { beginAtZero: true, grid: { color: '#e9ecef' }, ticks: { callback: (val) => '$' + val } } }
            }
        });
    }

    // Gráfico de barras: usuarios por segmento
    const ctxUsers = document.getElementById('usersChart')?.getContext('2d');
    if (ctxUsers) {
        new Chart(ctxUsers, {
            type: 'bar',
            data: {
                labels: ['Premium', 'Estándar', 'Básico', 'Nuevos', 'Inactivos'],
                datasets: [{
                    label: 'Cantidad de usuarios',
                    data: [1240, 2100, 3150, 890, 430],
                    backgroundColor: ['#1f7b8c', '#2980b9', '#3b8db0', '#5dade2', '#95a5a6'],
                    borderRadius: 8,
                    barPercentage: 0.65,
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: { position: 'top' },
                    tooltip: { callbacks: { label: (ctx) => `${ctx.raw} usuarios` } }
                },
                scales: {
                    y: { beginAtZero: true, grid: { color: '#e9ecef' }, title: { display: true, text: 'Usuarios' } },
                    x: { ticks: { font: { size: 11 } } }
                }
            }
        });
    }

    // Pequeño efecto de active en los enlaces del sidebar (solo UI demostrativa)
    const navLinks = document.querySelectorAll('.nav-sidebar a');
    navLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            // Si el enlace es el dashboard activo y no queremos recargar, evitamos actualización
            if (this.getAttribute('href') === '#') {
                e.preventDefault();
            }
            navLinks.forEach(l => l.classList.remove('active'));
            this.classList.add('active');
            // En móvil, cerrar el offcanvas tras navegar usando la API de Bootstrap
            if (window.innerWidth < 992) {
                const offcanvasInstance = bootstrap.Offcanvas.getInstance(sidebar);
                if (offcanvasInstance) offcanvasInstance.hide();
            }
        });
    });

    // Bootstrap tooltips opcionales (no necesario pero elegante)
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
})();