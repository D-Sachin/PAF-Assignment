import React from 'react';
import { Moon } from 'lucide-react';

const ThemeToggle = ({ onClick }) => {
  const handleClick = onClick || (() => {});

  return (
    <button
      type="button"
      onClick={handleClick}
      aria-label="Dark Mode"
      title="Dark Mode"
      className="inline-flex items-center justify-center p-2.5 rounded-xl border border-slate-200 bg-white text-slate-700 hover:bg-slate-50 transition-all"
    >
      <Moon className="w-4 h-4" />
    </button>
  );
};

export default ThemeToggle;