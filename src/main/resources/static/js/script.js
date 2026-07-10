//===== Main Script =====
document.addEventListener('DOMContentLoaded', function() {
    //===== Background Particles =====
    var particlesContainer = document.getElementById('particles'); // ✅ ID corregido
    var colors = ['#6366f1', '#22d3ee', '#a855f7', '#f97316', '#14b8a6', '#eab308'];

    for (var i = 0; i < 20; i++) {
        var particle = document.createElement('div');
        particle.classList.add('particle');
        var size = Math.random() * 6 + 2;
        var color = colors[Math.floor(Math.random() * colors.length)]; // ✅ Math.random corregido
        var left = Math.random() * 100;
        var duration = Math.random() * 12 + 8;
        var delay = Math.random() * 5;

        particle.style.width = size + 'px';
        particle.style.height = size + 'px';
        particle.style.background = color;
        particle.style.left = left + '%';
        particle.style.top = top + '%';
        particle.style.animationDuration = duration + 's';
        particle.style.animationDelay = delay + 's';

        particlesContainer.appendChild(particle); // ✅ método y variable correctos
    }

    //===== Card Mouse Tilt Effect =====
    var cards = document.querySelectorAll('.card');

    cards.forEach(function(card) {
        card.addEventListener('mousemove', function(e) {
            var rect = card.getBoundingClientRect();
            var x = e.clientX - rect.left;
            var y = e.clientY - rect.top;
            var centerX = rect.width / 2;
            var centerY = rect.height / 2;
            var rotateX = (y - centerY) / 30;   // ✅ Rotación en Y basada en X
            var rotateY = (centerX - x) / 30;   // ✅ Rotación en X basada en Y
            card.style.transform = 'translateY(-8px) perspective(1000px) rotateX(' + rotateX + 'deg) rotateY(' + rotateY + 'deg)';
        });

        card.addEventListener('mouseleave', function() {
            card.style.transform = ''; // Vuelve a la posición original
        });
    });
});